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
            System.setErr(out);

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
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void binarySearch() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("binary_search.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void bubbleSort() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("bubble_sort.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void stack() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("stack.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void instanceOf() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("instance_of.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void castType() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("cast_type.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void calculator() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("calculator.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("", baos.toString());
        }
    }

    @Test
    void raiseException() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("raise_exception.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("Do something useful ...\n" +
                    "MyBusinessException [ message = A message that describes the error. ]\n" +
                    "    at do_something_else:14\n" +
                    "    at perform_business_operation:5\n" +
                    "    at raise_exception.toy:1\n", baos.toString());
        }
    }

    @Test
    void handleException() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("handle_exception.toy");
        Path path = Paths.get(resource.toURI());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(baos)) {

            System.setOut(out);
            System.setErr(out);

            ToyLanguage lang = new ToyLanguage();
            lang.execute(path);

            assertEquals("Do something useful ...\n" +
                    "Rescuing 'A message that describes the error.'\n" +
                    "Ensure block\n", baos.toString());
        }
    }
}