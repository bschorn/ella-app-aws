package org.schorn.ella.aws.file.s3;

// Here is Ella App (Files)

import org.schorn.ella.app.file.AppFiles;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class AWSFileTest {

    static String TEST_CONTENT = "This is a test file.\nThis is only a test file.\nHad this been a real file....\n";


    static public void main(String[] args) {
        try {

            // Here is the mapping the implementation to the interface (poor man's bean)
            System.setProperty("org.schorn.ella.app.file.IAppFiles","org.schorn.ella.aws.file.s3.S3Files");

            Path path = Paths.get("./products/test-file.txt");

            // Write test to path
            AppFiles.write(path, TEST_CONTENT.getBytes(StandardCharsets.UTF_8));

            // Get file contents - using readAllBytes
            String testContent1 = new String(AppFiles.readAllBytes(path), StandardCharsets.UTF_8);
            System.out.println(testContent1);

            // Get file contents - using readAllLines
            String testContent2 = AppFiles.readAllLines(path).stream().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(testContent2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

