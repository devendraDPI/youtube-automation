package demo.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    /**
     * Reads data from an Excel file and returns it as a two-dimensional array of objects.
     *
     * @param filePath The file path of the Excel file to read from.
     * @param sheetName The name of the sheet within the Excel file to read data from.
     * @param columnIndex The index of the column from which to extract data.
     * @return A two-dimensional array of objects containing the data read from the Excel file,
     *         each row represents an array of objects containing data from a single cell.
     */
    public static Object[][] readDataFromExcel(String filePath, String sheetName, int columnIndex) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            YouTubeUtils.logStatus("readDataFromExcel", "Getting data from excel file");

            // Access the specified sheet within the Excel workbook
            Sheet sheet = workbook.getSheet(sheetName);

            // Throw an exception if the specified sheet is not found
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found!");
            }

            // List to store records read from the Excel sheet
            List<Object[]> records = new ArrayList<>();

            // Get the index of the first and last rows in the sheet
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();

            // Iterate over each row in the sheet
            for (int i=firstRowNum+1; i<=lastRowNum; i++) {
                // Get the current row
                Row row = sheet.getRow(i);
                if (row != null) {
                    // Get the cell at the specified column index
                    Cell cell = row.getCell(columnIndex);

                    // Extract the cell value
                    Object value = getCellValue(cell);

                    // Add the cell value to the list of records
                    records.add(new Object[]{value});
                }
            }

            // Convert the list of records to a two-dimensional array and return
            return records.toArray(new Object[0][]);
        } catch (Exception e) {
            YouTubeUtils.logStatus("readDataFromExcel", "Exception\n\t\t\t" + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves the value of a cell in an Excel sheet.
     *
     * @param cell The cell from which to retrieve the value.
     * @return The value of the cell as an Object, or null if the cell is null.
     */
    private static Object getCellValue(Cell cell) {
        // Check if the cell is null
        if (cell == null) {
            return null;
        }

        // Determine the cell type and return the appropriate value
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return null;
        }
    }
}
