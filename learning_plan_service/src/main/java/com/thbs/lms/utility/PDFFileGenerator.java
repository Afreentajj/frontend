package com.thbs.lms.utility;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PDFFileGenerator {
    // Private constructor to prevent instantiation
    private PDFFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static File generatePDFFile(String filePath) throws IOException {
        File pdfFile = new File(filePath);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("This is a sample PDF file.");
                contentStream.endText();
            }

            document.save(pdfFile);
        }

        return pdfFile;
    }

    public static MockMultipartFile convertToMockMultipartFile(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            return new MockMultipartFile("file", file.getName(), "application/pdf", output.toByteArray());
        }
    }

}
