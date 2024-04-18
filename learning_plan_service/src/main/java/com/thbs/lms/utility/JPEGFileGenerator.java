package com.thbs.lms.utility;

import javax.imageio.ImageIO;

import org.springframework.mock.web.MockMultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JPEGFileGenerator {
    // Private constructor to prevent instantiation
    private JPEGFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static File generateJPEGFile(String filePath) throws IOException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        File jpegFile = new File(filePath);
        ImageIO.write(image, "jpeg", jpegFile);
        return jpegFile;
    }

    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(input);
        ImageIO.write(image, "jpeg", output);
        return new MockMultipartFile("file", file.getName(), "image/jpeg", output.toByteArray());
    }
}
