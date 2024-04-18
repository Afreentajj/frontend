package com.thbs.lms.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.mock.web.MockMultipartFile;

public class PNGFileGenerator {
    // Private constructor to prevent instantiation
    private PNGFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static File generatePNGFile(String filePath) throws IOException {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        File pngFile = new File(filePath);
        ImageIO.write(image, "png", pngFile);
        return pngFile;
    }

    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage image = ImageIO.read(input);
        ImageIO.write(image, "png", output);
        return new MockMultipartFile("file", file.getName(), "image/png", output.toByteArray());
    }
}
