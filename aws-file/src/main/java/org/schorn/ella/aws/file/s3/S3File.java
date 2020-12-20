package org.schorn.ella.aws.file.s3;

import org.schorn.ella.app.IFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;


/**
 *
 */
public class S3File implements IFile, AutoCloseable {

    private final S3Client syncClient = S3Client.create();
    private final S3AsyncClient asyncClient = S3AsyncClient.create();

    private GetObjectRequest createGetRequest(String bucket, String key) {
        return GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
    }


    @Override
    public IReadResponse read(ReadRequest readRequest) {
        try {
            S3Path s3Path = S3Path.of(readRequest.getPath());
            GetObjectRequest getObjectRequest = createGetRequest(s3Path.bucket(), s3Path.key());
            ResponseBytes<GetObjectResponse> objectBytes = syncClient.getObjectAsBytes(getObjectRequest);
            return EllaReadResponse.create(readRequest.getCharset(), objectBytes);
        } catch (Exception exception) {
            return EllaReadResponse.create(exception);
        }
    }

    @Override
    public IAsyncReadResponse asyncRead(ReadRequest readRequest) {
        try {


        } catch (Throwable throwable) {

        }
        return null;
    }


    @Override
    public IWriteResponse write(WriteRequest writeRequest) {
        try {
            /*
            s3Client.putObject(PutObjectRequest.builder().bucket(s3Path.bucket()).key(s3Path.key()).build(),
                    RequestBody.fromBytes(bytes));
            */
        } catch (Throwable throwable) {

        }
        return null;
    }

    @Override
    public IAsyncWriteResponse asyncWrite(WriteRequest writeRequest) {
        return null;
    }


    @Override
    public void close() throws Exception {
        Exception ex = null;
        try {
            syncClient.close();
        } catch (Exception ex1) {
            ex = ex1;
        } finally {
            asyncClient.close();
        }
        if (ex != null) {
            throw ex;
        }
    }
}
