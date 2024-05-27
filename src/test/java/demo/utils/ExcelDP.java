package demo.utils;

import java.io.File;

import org.testng.annotations.DataProvider;

public class ExcelDP {
    /**
     * Provides data for searching.
     *
     * @return A two-dimensional array of objects containing search terms.
     */
    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        // Path to the Excel file containing search terms
        String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "itemsToSearch.xlsx";

        // Name of the worksheet within the Excel file
        String worksheetName = "Sheet1";

        // Column index containing the search terms
        int columnIndex = 0;

        // Retrieve data from the Excel file using ExcelUtils
        return ExcelUtils.readDataFromExcel(filePath, worksheetName, columnIndex);
    }
}
