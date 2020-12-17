package org.schorn.ella.aws.file.s3;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 *
 */
public class S3Region implements AutoCloseable {
    private final Region region;
    private final S3Client s3Client;

    static S3Region create(Region region) throws Exception {
        S3Client s3Client = S3Client.builder().region(region).build();
        return new S3Region(region, s3Client);
    }

    private S3Region(Region region, S3Client s3Client) {
        this.region = region;
        this.s3Client = s3Client;
    }

    public Region getRegion() {
        return this.region;
    }

    public S3Client getClient() {
        return this.s3Client;
    }

    @Override
    public void close() throws Exception {
        s3Client.close();
    }
}
