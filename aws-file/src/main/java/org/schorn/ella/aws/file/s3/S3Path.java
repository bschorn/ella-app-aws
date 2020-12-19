package org.schorn.ella.aws.file.s3;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringJoiner;

public class S3Path {
    public static S3Path of(Path path) {
        return new S3Path(path);
    }
    private final String bucket;
    private final String key;
    private S3Path(Path path) {
        String[] parts = path.toString().split("[\\\\/]");
        Deque<String> deck = new LinkedList<>();
        Arrays.asList(parts).forEach(element -> deck.addLast(element));
        while (deck.peekFirst().equals("") || deck.peekFirst().equals(".")) {
            deck.removeFirst();
        }
        this.bucket = deck.removeFirst();
        StringJoiner joiner = new StringJoiner("/","","");
        deck.stream().forEachOrdered(element -> joiner.add(element));
        this.key = joiner.toString();
    }

    public String bucket() {
        return this.bucket;
    }
    public String key() {
        return this.key;
    }
}
