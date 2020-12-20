package org.schorn.ella.aws.file.s3;

import org.schorn.ella.app.IFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 *
 */
public class EllaReadResponse implements IFile.IReadResponse {

    public static EllaReadResponse create(Charset charset, ResponseBytes<GetObjectResponse> s3response) {
        return new EllaReadResponse(charset, s3response);
    }
    public static EllaReadResponse create(Exception exception) {
        return new EllaReadResponse(exception);
    }

    private final Charset charset;
    private final ResponseBytes<GetObjectResponse> s3response;
    private Exception exception = null;
    private EllaReadResponse(Charset charset, ResponseBytes<GetObjectResponse> s3response) {
        this.charset = charset;
        this.s3response = s3response;
    }

    private EllaReadResponse(Exception exception) {
        this.exception = exception;
        this.s3response = null;
        this.charset = null;
    }

    @Override
    public Optional<Exception> getException() {
        return Optional.ofNullable(this.exception);
    }

    @Override
    public String toString() {
        return this.s3response.asString(this.charset);
    }

    @Override
    public byte[] toByteArray() {
        return this.s3response.asByteArray();
    }

    @Override
    public List<String> asList() {
        return Arrays.asList(this.s3response.asString(this.charset).split(System.lineSeparator()));
    }

}
