package org.schorn.ella.aws.file.s3;

import org.schorn.ella.app.IFile;

import java.util.Optional;

/**
 *
 */
public class S3Response {
    static class ReadResponse implements IFile.IReadResponse {

        private Exception exception = null;
        ReadResponse() {

        }

        @Override
        public Optional<Exception> getException() {
            return Optional.empty();
        }
    }
    static class WriteResponse implements IFile.IWriteResponse {

        @Override
        public Optional<Exception> getException() {
            return Optional.empty();
        }
    }
}
