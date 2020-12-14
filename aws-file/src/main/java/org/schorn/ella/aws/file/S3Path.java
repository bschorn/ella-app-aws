package org.schorn.ella.aws.file;

import software.amazon.awssdk.regions.Region;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringJoiner;

public class S3Path {

    static public S3Path of(Path path) {
        return new S3Path(path);
    }
    private final Region region;
    private final String bucket;
    private final String key;
    private S3Path(Path path) {
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
        this.bucket = deck.removeFirst();
        StringJoiner joiner = new StringJoiner("/","","");
        deck.stream().forEachOrdered(element -> joiner.add(element));
        this.key = joiner.toString();
    }

    public Region region() {
        return this.region;
    }
    public String bucket() {
        return this.bucket;
    }
    public String key() {
        return this.key;
    }
}
