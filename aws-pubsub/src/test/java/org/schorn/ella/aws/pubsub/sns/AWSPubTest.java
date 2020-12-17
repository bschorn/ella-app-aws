package org.schorn.ella.aws.pubsub.sns;

import org.schorn.ella.app.topic.AppTopics;
import org.schorn.ella.app.topic.IAppTopic;
import org.schorn.ella.app.topic.publish.AppPublish;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AWSPubTest {
    static String TEST_CONTENT = "This is a test file.\nThis is only a test file.\nHad this been a real file....\n";


    static public void main(String[] args) {
        try {

            // Here is the mapping the implementation to the interface (poor man's bean)
            System.setProperty("org.schorn.ella.app.topic.IAppTopics","org.schorn.ella.aws.pubsub.AWSTopics");
            System.setProperty("org.schorn.ella.app.topic.publish.IAppPublish","org.schorn.ella.aws.pubsub.sns.SNSPublish");

            String content = TEST_CONTENT + UUID.randomUUID().toString();

            // Get a topic
            IAppTopic topic = AppTopics.getTopic("JaneBankApp_AccountUpdate.fifo");

            // Write test to path
            AppPublish.publish(topic, content.getBytes(StandardCharsets.UTF_8));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
