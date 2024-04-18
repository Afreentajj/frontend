package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DuplicateTopicExcelFileGenerator {
    // Private constructor to prevent instantiation
    private DuplicateTopicExcelFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static MockMultipartFile generateDuplicateTopicExcelFile(String filePath) throws IOException {
        try(Workbook workbook = new XSSFWorkbook()){
            // Create a new sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Level");
            headerRow.createCell(1).setCellValue("BASIC");

            // Create data rows with duplicate topic names
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("DuplicateTopic");
            dataRow1.createCell(1).setCellValue("Data1Desc");

            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue("DuplicateTopic");
            dataRow2.createCell(1).setCellValue("Data2Desc");

            // Write workbook content to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] content = outputStream.toByteArray();

            // Create MockMultipartFile
            return new MockMultipartFile(
                    "file",
                    "duplicate_topic_excel.xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    content);
        }
    }
}
