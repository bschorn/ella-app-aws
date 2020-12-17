package org.schorn.ella.aws.pubsub;

import org.schorn.ella.app.topic.IAppTopic;
import org.schorn.ella.app.topic.IAppTopics;

public class AWSTopics implements IAppTopics {
    @Override
    public IAppTopic getTopic(String topic) {
        return AWSTopic.get(topic);
    }
}
