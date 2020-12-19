package org.schorn.ella.aws.file.s3;

import org.apache.commons.io.IOUtils;
import org.schorn.ella.app.IFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 */
public class EllaReadResponse<T> implements IFile.IReadResponse<T> {

    public static EllaReadResponse create(IFile.ReadRequest readRequest, ResponseInputStream<GetObjectResponse> s3response) {
        return new EllaReadResponse<Stream<String>>(readRequest.getFormat(), readRequest.getCharset(), s3response);
    }
    public static EllaReadResponse create(Exception exception) {
        return new EllaReadResponse(exception);
    }

    private final IFile.Format format;
    private final Charset charset;
    private final ResponseInputStream<GetObjectResponse> response;
    private Exception exception = null;
    private EllaReadResponse(IFile.Format format, Charset charset, ResponseInputStream<GetObjectResponse> response) {
        this.format = format;
        this.charset = charset;
        this.response = response;
    }

    private EllaReadResponse(Exception exception) {
        this.exception = exception;
        this.response = null;
        this.format = null;
        this.charset = null;
    }

    @Override
    public Optional<Exception> getException() {
        return Optional.ofNullable(this.exception);
    }

    @Override
    public byte[] asBytes() {
        try {
            return IOUtils.toByteArray(response);
        } catch (IOException e) {
            this.exception = e;
        }
        return new byte[0];
    }

}
