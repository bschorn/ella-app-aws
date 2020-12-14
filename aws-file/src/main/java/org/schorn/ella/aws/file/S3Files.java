package org.schorn.ella.aws.file;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.schorn.ella.app.file.IAppFiles;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 *
 */
public class S3Files implements IAppFiles, AutoCloseable {

    private final S3Region defaultRegion;
    private final Map<Region,S3Region> otherRegions = new HashMap<>();

    public S3Files() {
        S3Region defaultRegion = null;
        try {
            defaultRegion = S3Region.create(Region.US_EAST_1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.defaultRegion = defaultRegion;
    }

    @Override
    public boolean isDirectory(Path path) {
        return false;
    }

    @Override
    public boolean isReadable(Path path) {
        return false;
    }

    @Override
    public boolean isWritable(Path path) {
        return false;
    }

    @Override
    public Stream<String> lines(Path path) {
        S3Path s3Path = S3Path.of(path);
        GetObjectRequest request = GetObjectRequest.builder().bucket(s3Path.bucket()).key(s3Path.key()).build();
        String result;
        try (ResponseInputStream<GetObjectResponse> response = this.defaultRegion.getClient().getObject(request)) {
            return new BufferedReader(new InputStreamReader(response, Charsets.toCharset("UTF-8"))).lines();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Stream<String> lines(Path path, Charset charset) {
        return null;
    }

    @Override
    public Stream<Path> list(Path path) {
        return null;
    }

    @Override
    public byte[] readAllBytes(Path path) {
        S3Path s3Path = S3Path.of(path);
        GetObjectRequest request = GetObjectRequest.builder().bucket(s3Path.bucket()).key(s3Path.key()).build();
        byte[] result;
        try {
            ResponseInputStream<GetObjectResponse> response = this.defaultRegion.getClient().getObject(request);
            result = IOUtils.toByteArray(response);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return result;
    }

    @Override
    public List<String> readAllLines(Path path) {
        return readAllLines(path, Charsets.toCharset("UTF-8"));
    }

    @Override
    public List<String> readAllLines(Path path, Charset charset) {
        S3Path s3Path = S3Path.of(path);
        GetObjectRequest request = GetObjectRequest.builder().bucket(s3Path.bucket()).key(s3Path.key()).build();
        String result;
        try {
            ResponseInputStream<GetObjectResponse> response = this.defaultRegion.getClient().getObject(request);
            result = IOUtils.toString(response, charset);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return Arrays.asList(result.split(System.lineSeparator()));
    }

    @Override
    public long size(Path path) {
        return 0;
    }

    @Override
    public Path write(Path path, byte[] bytes) {
        S3Path s3Path = S3Path.of(path);
        this.defaultRegion.getClient().putObject(PutObjectRequest.builder().bucket(s3Path.bucket()).key(s3Path.key()).build(),
                RequestBody.fromBytes(bytes));
        return path;
    }

    @Override
    public Path write(Path path, Iterable<? extends CharSequence> iterable, Charset charset, OpenOption... openOptions) {
        return null;
    }

    @Override
    public Path write(Path path, Iterable<? extends CharSequence> iterable, OpenOption... openOptions) {
        return null;
    }

    @Override
    public void close() throws Exception {
        this.defaultRegion.close();
    }
}
