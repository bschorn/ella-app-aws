package org.schorn.ella.aws.file.s3;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class TestS3 {

    public static void main(String[] args) {
        try {
            S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket("jane-bank.bank-ref")
                    .key("products/usa-ny.json")
                    .build();
            //s3Client.getObject(getObjectRequest);
            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            byte[] data = objectBytes.asByteArray();
            System.out.println(new String(data));

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
