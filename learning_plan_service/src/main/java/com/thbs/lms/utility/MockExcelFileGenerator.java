package com.thbs.lms.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MockExcelFileGenerator {
    // Private constructor to prevent instantiation
    private MockExcelFileGenerator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static MockMultipartFile generateMockExcelFile() throws IOException {
        // Create a new workbook
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

        // Create a new sheet
        Sheet sheet = workbook.createSheet("Sheet1");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Level");
        headerRow.createCell(1).setCellValue("BASIC");
        
        // Create data rows
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Data1");
        dataRow1.createCell(1).setCellValue("Data1Desc");
        
        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Data2");
        dataRow2.createCell(1).setCellValue("Data2Desc");
        
        // Write workbook content to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] content = outputStream.toByteArray();
        
        // Create MockMultipartFile
        return new MockMultipartFile(
                "file",
                "mock_excel.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                content);
                
        }
    }
}

