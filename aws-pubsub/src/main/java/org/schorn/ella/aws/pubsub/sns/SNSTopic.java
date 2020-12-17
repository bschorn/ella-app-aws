package org.schorn.ella.aws.pubsub.sns;

import org.schorn.ella.app.topic.IAppTopic;
import software.amazon.awssdk.regions.Region;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 *
 */
public class SNSTopic implements IAppTopic {
    static public SNSTopic of(Path path) {
        return new SNSTopic(path);
    }

    private final Region region;

    private SNSTopic(Path path) {
        Region region = Region.US_EAST_1;
        Deque<String> deck = new LinkedList<>();
        String[] parts = path.toString().split("[\\\\/]");
        Arrays.asList(parts).forEach(element -> deck.addLast(element));
        if (deck.peekFirst().equals("")) {
            deck.removeFirst();
        }
        if (deck.peekFirst().equals(".")) {
            deck.removeFirst();
        } else {
            Region r0 = Region.of(deck.peekFirst());
            if (r0 != null) {
                region = r0;
                deck.removeFirst();
            }
        }
        this.region = region;
        StringJoiner joiner = new StringJoiner("/","","");
        deck.stream().forEachOrdered(element -> joiner.add(element));
    }

    public Region region() {
        return this.region;
    }

    @Override
    public String getTopic() {
        return null;
    }
}
