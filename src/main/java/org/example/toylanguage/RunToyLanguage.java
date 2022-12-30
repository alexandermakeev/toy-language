package org.example.toylanguage;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class RunToyLanguage {
    public static void main(String[] args) {
        Path sourceFilePath = FileSystems.getDefault().getPath(args[0]);
        if (Files.exists(sourceFilePath)) {
            new ToyLanguage().execute(sourceFilePath);
        }
        else {
            System.out.println("Cannot resolve args[0] [" + sourceFilePath + "] as a valid file path.");
        }
    }
}
