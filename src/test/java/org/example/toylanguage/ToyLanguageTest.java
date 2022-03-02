package org.example.toylanguage;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToyLanguageTest {

    @Test
    void execute() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("test.toy");
        Path path = Paths.get(resource.toURI());

        try (InputStream in = new ByteArrayInputStream("Robert\n5\nyes".getBytes());
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setIn(in);
            System.setOut(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals(
                    "enter \"your name\" >>> " +
                            "enter \"your experience in years\" >>> " +
                            "enter \"do you like programming\" >>> " +
                            "Person [ name = Robert, experience = 5, is_developer = true ]\n" +
                            "hey Robert!\n" +
                            "you had started your career in 2017\n",
                    baos.toString()
            );
        }
    }

}