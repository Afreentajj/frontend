package com.thbs.lms.utility;

import org.springframework.mock.web.MockMultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileGenerator {
    // Private constructor to prevent instantiation
    private TextFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static File generateTextFile(String filePath) throws IOException {
        File textFile = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            writer.write("This is a sample text file.");
        }
        return textFile;
    }

    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        byte[] fileBytes = Files.readAllBytes(path);
        return new MockMultipartFile("file", file.getName(), "text/plain", fileBytes);
    }
}
