package com.thbs.lms.utility;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.thbs.lms.exception.InvalidSheetFormatException;

public class SheetValidator {
    // Private constructor to prevent instantiation
    private SheetValidator() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isValidSheetFormat(Sheet sheet) {
        // Check if the sheet has at least one row
        if (sheet.getPhysicalNumberOfRows() < 1) {
            throw new InvalidSheetFormatException("Sheet does not contain any rows.");
        }

        // Check if the first row (header row) has the correct format
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new InvalidSheetFormatException("Header row is missing.");
        }
        // Check if the first cell (A1) contains "Level"
        Cell firstCell = headerRow.getCell(0);
        if (firstCell == null || !firstCell.getStringCellValue().trim().equalsIgnoreCase("Level")) {
            throw new InvalidSheetFormatException("Header cell A1 must contain 'Level'.");
        }

        // Check if the second cell (B1) contains "BASIC", "INTERMEDIATE", or "ADVANCED"
        Cell levelCell = headerRow.getCell(1);
        if (levelCell == null) {
            throw new InvalidSheetFormatException("Sheet must have two columns.");
        }

        String level = levelCell.getStringCellValue().trim();
        if (!level.equalsIgnoreCase("BASIC") && !level.equalsIgnoreCase("INTERMEDIATE")
                && !level.equalsIgnoreCase("ADVANCED")) {
            throw new InvalidSheetFormatException(
                    "Header cell B1 must contain 'BASIC', 'INTERMEDIATE', or 'ADVANCED'.");
        }

        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum < 1) {
            throw new InvalidSheetFormatException("No topics found in the course.");
        }

        // Get iterator for the rows in the sheet
        Iterator<Row> rowIterator = sheet.iterator();
        // Skip the header row
        rowIterator.next();
        // Process each subsequent row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell topicNameCell = row.getCell(0);
            Cell topicDescriptionCell = row.getCell(1);
            if (topicNameCell != null && topicDescriptionCell == null) {
                throw new InvalidSheetFormatException("Topic does not have a description.");
            }
        }

        return true;
    }
}
