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
    void fibonacciNumber() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("fibonacci_number.toy");
        Path path = Paths.get(resource.toURI());

        try (InputStream in = new ByteArrayInputStream("11".getBytes());
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setIn(in);
            System.setOut(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals(
                    "enter \"index number\" >>> " +
                            "fibonacci number is 89\n",
                    baos.toString()
            );
        }
    }

    @Test
    void isSameTree() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("is_same_tree.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals(
                    "true\n" +
                            "false\n" +
                            "false\n" +
                            "false\n" +
                            "false\n" +
                            "false\n",
                    baos.toString()
            );
        }
    }

}