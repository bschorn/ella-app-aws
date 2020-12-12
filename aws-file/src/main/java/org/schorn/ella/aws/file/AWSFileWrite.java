package org.schorn.ella.aws.file;

import org.schorn.ella.app.access.IAppAccess;
import org.schorn.ella.app.file.IAppWrite;
import org.schorn.ella.app.key.IAppKey;
import org.schorn.ella.app.receipt.IAppReceipt;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;

/**
 *
 */
public class AWSFileWrite implements IAppWrite, AutoCloseable {

    static public AWSFileWrite create(Region region, String bucketName) throws Exception {
        return new AWSFileWrite(region, bucketName);
    }

    private final Region region;
    private final String bucket;
    private S3Client s3;

    private AWSFileWrite(Region region, String bucket) {
        this.region = Region.US_EAST_1;
        this.bucket = bucket;
        this.s3 = S3Client.builder().region(region).build();
    }

    @Override
    public IAppAccess getAccess(IAppKey appKey) {
        AWSFileAccess.Builder accessBldr = AWSFileAccess.builder();
        return accessBldr.build();
    }

    @Override
    public IAppReceipt write(URI uri, Object object) {
        AWSFileKey fileKey = AWSFileKey.builder().set(uri).build();
        return write(fileKey, object);
    }

    @Override
    public IAppReceipt write(IAppKey appKey, Object object) {
        AWSFileReceipt.Builder builder = AWSFileReceipt.builder();
        this.s3.putObject(PutObjectRequest.builder().bucket(this.bucket).key(appKey.toString()).build(),
                RequestBody.fromString("write object to S3"));
        return builder.build();
    }


    static public void main(String[] args) {
        try {
            AWSFileWrite fileWriter = AWSFileWrite.create(Region.US_EAST_1, "jane-bank.bank-ref");

            String content = "[{\"product_code\":\"SDA0001\",\"product_name\":\"Savings Account\"},{\"product_code\":\"DDA0001\",\"product_name\":\"Checking Account\"},{\"product_code\":\"TDF0001\",\"product_name\":\"Treasury Deposit\"},{\"product_code\":\"PLA0001\",\"product_name\":\"Personal Loan\"},{\"product_code\":\"MBJ0001\",\"product_name\":\"Jumbo Mortgage Loan\"}]";
            fileWriter.write(URI.create("file:///products/usa-*"), content);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        this.s3.close();
    }
}
