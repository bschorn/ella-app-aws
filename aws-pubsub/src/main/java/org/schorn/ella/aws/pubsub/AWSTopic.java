package org.schorn.ella.aws.pubsub;


import org.schorn.ella.app.topic.IAppTopic;

public class AWSTopic implements IAppTopic {

    static AWSTopic get(String topic) {
        return new AWSTopic(topic);
    }

    private String topic;
    private AWSTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }
}