package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InvalidExtraDataExcelFileGenerator {
    // Private constructor to prevent instantiation
    private InvalidExtraDataExcelFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static MockMultipartFile generateInvalidExtraDataExcelFile(String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {

            // Create a new sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Level");
            headerRow.createCell(1).setCellValue("BASIC");

            // Create data rows
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("topic1");
            dataRow1.createCell(1).setCellValue("desc1");
            dataRow1.createCell(2).setCellValue("ExtraData1"); // Data in cell C1

            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue("topic2");
            dataRow2.createCell(1).setCellValue("desc2");

            Row dataRow3 = sheet.createRow(3);
            dataRow3.createCell(3).setCellValue("ExtraData2"); // Data in cell D4

            Row dataRow4 = sheet.createRow(5);
            dataRow4.createCell(5).setCellValue("ExtraData3"); // Data in cell F6

            // Write workbook content to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] content = outputStream.toByteArray();

            // Create MockMultipartFile
            return new MockMultipartFile(
                    "file",
                    "invalid_extra_data_excel.xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    content);
        }

    }
}
