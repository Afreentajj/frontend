package com.thbs.lms.testController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.thbs.lms.utility.MockExcelFileGenerator;
import com.thbs.lms.utility.JPEGFileGenerator;
import com.thbs.lms.utility.PDFFileGenerator;
import com.thbs.lms.utility.PNGFileGenerator;
import com.thbs.lms.utility.TextFileGenerator;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BulkUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFileUploadSuccess() throws Exception {
        MockMultipartFile file = MockExcelFileGenerator.generateMockExcelFile();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/learning-plan/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File uploaded successfully."));
    }

    @Test
    void pdfFileUploadFailure() throws Exception {
        String filePath = "sample.pdf";
        File pdfFile = PDFFileGenerator.generatePDFFile(filePath);

        MockMultipartFile file = PDFFileGenerator.convertToMockMultipartFile(pdfFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/learning-plan/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

    @Test
    void pngFileUploadFailure() throws Exception {
        String filePath = "sample.png";
        File pngFile = PNGFileGenerator.generatePNGFile(filePath);

        MockMultipartFile file = PNGFileGenerator.convertToMockMultipartFile(pngFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/learning-plan/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

    @Test
    void textFileUploadFailure() throws Exception {
        String filePath = "sample.txt";
        File textFile = TextFileGenerator.generateTextFile(filePath);

        MockMultipartFile file = TextFileGenerator.convertToMockMultipartFile(textFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/learning-plan/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

    @Test
    void jpegFileUploadFailure() throws Exception {
        String filePath = "sample.jpeg";
        File jpegFile = JPEGFileGenerator.generateJPEGFile(filePath);

        MockMultipartFile file = JPEGFileGenerator.convertToMockMultipartFile(jpegFile);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/learning-plan/upload").file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error processing the uploaded file."));
    }

}