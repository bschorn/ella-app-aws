package org.schorn.ella.aws.pubsub.sns;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

public class SNSRegion implements AutoCloseable {
    private final Region region;
    private final SnsClient snsClient;

    static SNSRegion create(Region region) throws Exception {
        SnsClient snsClient = SnsClient.builder().region(region).build();
        return new SNSRegion(region, snsClient);
    }

    private SNSRegion(Region region, SnsClient snsClient) {
        this.region = region;
        this.snsClient = snsClient;
    }

    public Region getRegion() {
        return this.region;
    }

    public SnsClient getClient() {
        return this.snsClient;
    }

    @Override
    public void close() throws Exception {
        snsClient.close();
    }

}
