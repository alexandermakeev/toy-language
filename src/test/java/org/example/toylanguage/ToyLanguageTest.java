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

    @Test
    void binarySearch() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("binary_search.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals(
                    "0\n" +
                            "5\n" +
                            "9\n" +
                            "-1\n" +
                            "-1\n" +
                            "-1\n",
                    baos.toString()
            );
        }
    }

    @Test
    void bubbleSort() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("bubble_sort.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals(
                    "[-1, 14, 12, 10, 8, 6, 4, 2, 0, 15, 13, 11, 9, 7, 5, 3, 1, -1, 14, 12]\n" +
                            "false\n" +
                            "[-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 12, 13, 14, 14, 15]\n" +
                            "true\n",
                    baos.toString()
            );
        }
    }

}