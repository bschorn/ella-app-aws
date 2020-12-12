package org.schorn.ella.aws.file;

import org.schorn.ella.app.key.IAppKey;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

public class AWSFileKey implements IAppKey {

    static public Region REGION = Region.of(System.getProperty("AWS_S3_REGION", "US_EAST_1"));
    static public String BUCKET = System.getProperty("AWS_S3_BUCKET","jane-bank.bank-ref");

    private final Region region;
    private final String bucket;
    private final String key;

    private AWSFileKey(AWSFileKey.Builder builder) {
        this.region = builder.region;
        this.bucket = builder.bucket;
        this.key = builder.key;
    }

    Region getRegion() {
        return this.region;
    }
    String getBucket() {
        return this.bucket;
    }
    String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return this.key;
    }

    static AWSFileKey.Builder builder(Region region, String bucket) {
        return new AWSFileKey.Builder(region, bucket);
    }
    static AWSFileKey.Builder builder() {
        return new AWSFileKey.Builder(null,null);
    }

    /**
     * AWSFileKey.Builder
     */
    static class Builder {

        final Region region;
        final String bucket;
        String key = null;
        URI uri = null;

        private Builder(Region region, String bucket) {
            this.region = region != null ? region : AWSFileKey.REGION;
            this.bucket = bucket != null ? bucket : AWSFileKey.BUCKET;
        }

        AWSFileKey.Builder set(String key) {
            this.key = key;
            return this;
        }

        AWSFileKey.Builder set(URI uri) {
            this.uri = uri;
            return this;
        }

        AWSFileKey build() {
            if (this.uri != null) {
                this.key = this.uri.getPath();
            }
            return new AWSFileKey(this);
        }
    }

}
