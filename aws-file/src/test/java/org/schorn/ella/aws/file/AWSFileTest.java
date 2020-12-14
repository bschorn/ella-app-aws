package org.schorn.ella.aws.file;

// Here is Ella App (Files)
import org.schorn.ella.app.file.AppFiles;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class AWSFileTest {

    static public void main(String[] args) {
        try {
            // Here is the mapping the implementation to the interface (poor man's bean)
            System.setProperty("org.schorn.ella.app.file.IAppFiles","org.schorn.ella.aws.file.S3Files");
            // Create a Path object
            Path path = Path.of(new URI("file:///us-east-1/jane-bank.bank-ref/products/usa-ny.json"));
            // Get file contents - using readAllLines
            String contents = AppFiles.readAllLines(path).stream().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(contents);
            // get file contents = using lines (stream)
            contents = AppFiles.lines(path).collect(Collectors.joining(System.lineSeparator()));
            System.out.println(contents);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

