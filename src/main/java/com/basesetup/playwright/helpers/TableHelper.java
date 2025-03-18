package com.basesetup.playwright.helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Helper class for interacting with HTML tables using Playwright.
 * Supports dynamic row/column selection and interaction.
 */
public class TableHelper {
    private final Page page;
    private static final Logger logger = LoggerFactory.getLogger(TableHelper.class);

    public TableHelper(Page page) {
        this.page = page;
    }

    /**
     * Retrieves table data as a list of lists, where each inner list represents a row.
     *
     * @param tableLocator Table locator (CSS, XPath, etc.).
     * @return List of rows, where each row is a list of cell texts.
     */
    public List<List<String>> getTableData(Locator tableLocator) {
        try {
            List<Locator> rows = tableLocator.locator("tr").all();
            return rows.stream()
                    .map(row -> row.locator("td,th").allInnerTexts())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve table data: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Retrieves text from a specific table cell.
     *
     * @param tableLocator Table locator.
     * @param rowIndex     Row index (0-based).
     * @param colIndex     Column index (0-based).
     * @return Cell text, or empty string if out of bounds.
     */
    public String getCellText(Locator tableLocator, int rowIndex, int colIndex) {
        try {
            List<Locator> rows = tableLocator.locator("tr").all();
            if (rowIndex >= rows.size()) return "";

            List<Locator> cells = rows.get(rowIndex).locator("td,th").all();
            return (colIndex < cells.size()) ? cells.get(colIndex).innerText() : "";
        } catch (Exception e) {
            logger.error("Error getting cell text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Clicks on a table cell at the specified row and column index.
     *
     * @param tableLocator Table locator.
     * @param rowIndex     Row index (0-based).
     * @param colIndex     Column index (0-based).
     */
    public void clickCell(Locator tableLocator, int rowIndex, int colIndex) {
        try {
            List<Locator> rows = tableLocator.locator("tr").all();
            if (rowIndex < rows.size()) {
                List<Locator> cells = rows.get(rowIndex).locator("td,th").all();
                if (colIndex < cells.size()) {
                    cells.get(colIndex).click();
                    logger.info("Clicked on cell at row {} and column {}", rowIndex, colIndex);
                }
            }
        } catch (Exception e) {
            logger.error("Error clicking on cell: {}", e.getMessage());
        }
    }

    /**
     * Finds a row index by searching for text in a specific column.
     *
     * @param tableLocator Table locator.
     * @param colIndex     Column index (0-based).
     * @param searchText   Text to search for.
     * @return Row index (0-based) or -1 if not found.
     */
    public int findRowByCellText(Locator tableLocator, int colIndex, String searchText) {
        try {
            List<Locator> rows = tableLocator.locator("tr").all();
            for (int i = 0; i < rows.size(); i++) {
                List<Locator> cells = rows.get(i).locator("td,th").all();
                if (colIndex < cells.size() && cells.get(colIndex).innerText().equals(searchText)) {
                    return i;
                }
            }
        } catch (Exception e) {
            logger.error("Error finding row by text: {}", e.getMessage());
        }
        return -1;
    }

    /**
     * Finds the row where the first column matches the given text and clicks the last column (assumed to contain an icon/button).
     *
     * @param tableLocator Table locator.
     * @param searchText   Text to match in the first column.
     */
    public void clickIconByFirstColumnText(Locator tableLocator, String searchText) {
        try {
            List<Locator> rows = tableLocator.locator("tr").all();
            for (Locator row : rows) {
                List<Locator> cells = row.locator("td,th").all();
                if (!cells.isEmpty() && cells.get(0).innerText().trim().equals(searchText)) {
                    cells.get(cells.size() - 1).click();
                    logger.info("Clicked on icon in last column for row with text: {}", searchText);
                    return;
                }
            }
        } catch (Exception e) {
            logger.error("Error clicking icon: {}", e.getMessage());
        }
    }

    /**
     * Gets all column values for a given column index.
     *
     * @param tableLocator Table locator.
     * @param colIndex     Column index (0-based).
     * @return List of column values.
     */
    public List<String> getColumnValues(Locator tableLocator, int colIndex) {
        try {
            return tableLocator.locator("tr").all().stream()
                    .map(row -> {
                        List<Locator> cells = row.locator("td,th").all();
                        return (colIndex < cells.size()) ? cells.get(colIndex).innerText() : "";
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting column values: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Gets the number of rows in the table.
     *
     * @param tableLocator Table locator.
     * @return Number of rows.
     */
    public int getRowCount(Locator tableLocator) {
        try {
            return tableLocator.locator("tr").count();
        } catch (Exception e) {
            logger.error("Error getting row count: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Gets the number of columns in the table.
     *
     * @param tableLocator Table locator.
     * @return Number of columns in the first row.
     */
    public int getColumnCount(Locator tableLocator) {
        try {
            List<Locator> rows = tableLocator.locator("tr").all();
            return rows.isEmpty() ? 0 : rows.get(0).locator("td,th").count();
        } catch (Exception e) {
            logger.error("Error getting column count: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Verifies if a specific text exists in the table.
     *
     * @param tableLocator Table locator.
     * @param searchText   Text to search for.
     * @return True if text is found, otherwise false.
     */
    public boolean doesTableContainText(Locator tableLocator, String searchText) {
        try {
            return tableLocator.locator("td,th").allInnerTexts().stream()
                    .anyMatch(text -> text.equals(searchText));
        } catch (Exception e) {
            logger.error("Error checking table for text: {}", e.getMessage());
            return false;
        }
    }
}
