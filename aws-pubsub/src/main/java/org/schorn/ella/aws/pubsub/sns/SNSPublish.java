package org.schorn.ella.aws.pubsub.sns;

import org.schorn.ella.app.topic.IAppTopic;
import org.schorn.ella.app.topic.publish.IAppPublish;
import org.schorn.ella.aws.core.AWSArn;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.config.model.ResourceType;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SNSPublish implements IAppPublish, AutoCloseable {


    private final SNSRegion defaultRegion;
    private final Map<Region,SNSRegion> otherRegions = new HashMap<>();

    public SNSPublish() {
        SNSRegion defaultRegion = null;
        try {
            defaultRegion = SNSRegion.create(Region.US_EAST_1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.defaultRegion = defaultRegion;
    }

    private SnsClient getClient(Region region) {
        SnsClient snsClient = this.defaultRegion.getClient();
        if (! region.equals(defaultRegion.getRegion())) {
            SNSRegion snsRegion = this.otherRegions.get(region);
            if (snsRegion != null) {
                snsClient = snsRegion.getClient();
            } else {
                try {
                    snsRegion = SNSRegion.create(region);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                snsClient = snsRegion.getClient();
            }
        }
        return snsClient;
    }

    @Override
    public String publish(IAppTopic topic, byte[] bytes) {
        try {
            AWSArn awsArn = AWSArn.get(ResourceType.AWS_SNS_TOPIC, topic.getTopic());
            PublishRequest request = PublishRequest.builder()
                    .messageGroupId("1")
                    .message(new String(bytes, StandardCharsets.UTF_8))
                    .topicArn(awsArn.toString())
                    .build();

            PublishResponse result = getClient(awsArn.getRegion()).publish(request);
            System.out.println(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());
            return result.sdkHttpResponse().statusText().get();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @Override
    public void close() throws Exception {
        this.defaultRegion.close();
        for (SNSRegion snsRegion : this.otherRegions.values()) {
            snsRegion.close();
        }
    }

}
