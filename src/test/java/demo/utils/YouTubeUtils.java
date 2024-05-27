package demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YouTubeUtils {
    /**
     * Logs a status message with the specified method name and message to the console.
     *
     * @param methodName The name of the method associated with the log message.
     * @param message The message to be logged.
     */
    public static void logStatus(String methodName, String message) {
        System.out.println(String.format("%s | %s | %s", getDateTime("yyyy-MM-dd HH:mm:ss"), methodName, message));
    }

    /**
     * Logs a status message with the specified test case ID, test step, and test message to the console.
     *
     * @param testCaseID The ID of the test case associated with the log message.
     * @param testStep The step of the test case associated with the log message.
     * @param testMessage The message related to the test step.
     */
    public static void logStatus(String testCaseID, String testStep, String testMessage) {
        System.out.println(String.format("\n%s | %s | %s | %s\n", getDateTime("yyyy-MM-dd HH:mm:ss"), testCaseID, testStep, testMessage));
    }

    /**
     * Retrieves the current date and time in the specified format pattern.
     *
     * @param formatPattern The pattern used to format the date and time (e.g., "yyyy-MM-dd HH:mm:ss").
     * @return A string representing the current date and time formatted according to the specified pattern.
     */
    public static String getDateTime(String formatPattern) {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(formatPattern));
    }

    /**
     * Verifies if the current URL of the WebDriver contains the specified text.
     *
     * @param driver The WebDriver instance whose current URL is to be verified.
     * @param textToContain The text to be checked for existence in the current URL.
     * @return True if the current URL contains the specified text, false otherwise.
     */
    public static Boolean verifyCurrentUrlContains(WebDriver driver, String textToContain) {
        try {
            logStatus("verifyCurrentUrlContains", "Verifying current URL");

            // Get the current URL
            String actualUrl = driver.getCurrentUrl();

            // Check if the current URL contains the specified text
            return actualUrl.contains(textToContain);
        } catch (Exception e) {
            logStatus("verifyCurrentUrlContains", "Exception\n\t\t\t" + e.getMessage());
            return false;
        }
    }

    /**
     * Scrolls the viewport to make the specified element visible within the WebDriver's current viewport.
     *
     * @param driver The WebDriver instance on which the scrolling operation is performed.
     * @param locator The By locator used to identify the element to scroll to.
     */
    public static void scrollToViewport(WebDriver driver, By locator) {
        try {
            logStatus("scrollToViewport", "Scrolling to viewport");

            // Set up a WebDriverWait to wait for the element to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // JavaScript executor to scroll the viewport to the element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception e) {
            logStatus("scrollToViewport", "Exception\n\t\t\t" + e.getMessage());
        }
    }

    /**
     * Retrieves and prints the displayed message of an element identified by the specified locator.
     *
     * @param driver The WebDriver instance where the element is located.
     * @param locator The By locator used to identify the element containing the displayed message.
     */
    public static void getDisplayedMessage(WebDriver driver, By locator) {
        try {
            logStatus("getDisplayedMessage", "Printing displayed message");

            // Find the element containing the displayed message
            WebElement messageElement = driver.findElement(locator);

            // Print the displayed message
            logStatus("getDisplayedMessage", "Printing message\n" + messageElement.getText());
        } catch (Exception e) {
            logStatus("getDisplayedMessage", "Exception\n\t\t\t" + e.getMessage());
        }
    }

    /**
     * Scrolls to the extreme end of the page by continuously clicking on an element identified by the specified locator.
     *
     * @param driver The WebDriver instance to perform the scrolling operation.
     * @param locator The By locator used to identify the element to click for scrolling.
     */
    public static void scrollToExtreme(WebDriver driver, By locator) {
        try {
            logStatus("scrollToExtreme", "Scrolling to extreme");

            // Set up a WebDriverWait to wait for the element to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Scroll to the extreme end by continuously clicking on the element
            while (button.isDisplayed()) {
                button.click();
            }
        } catch (Exception e) {
            logStatus("scrollToExtreme", "Exception\n\t\t\t" + e.getMessage());
        }
    }

    /**
     * Retrieves a WebElement identified by the specified locator within the WebDriver's current context.
     *
     * @param driver The WebDriver instance where the element is located.
     * @param elementLocator The By locator used to identify the WebElement to retrieve.
     * @return The WebElement identified by the locator if found and visible within the specified timeout, otherwise returns null.
     */
    public static WebElement getElement(WebDriver driver, By elementLocator) {
        WebElement movieElement = null;
        try {
            logStatus("getElement", "Getting element");

            // Set up a WebDriverWait to wait for the element to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            movieElement = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        } catch (Exception e) {
            logStatus("getElement", "Exception\n\t\t\t" + e.getMessage());
        }
        return movieElement;
    }

    /**
    * Retrieves the text content of a child element located within a parent element identified by the provided locators.
    *
    * @param driver The WebDriver instance where the elements are located.
    * @param elementLocator The By locator used to identify the parent element.
    * @param childLocator The By locator used to identify the child element within the parent element.
    * @return The text content of the child element if found, otherwise an empty string.
    */
    public static String getElementText(WebDriver driver, By elementLocator, By childLocator) {
        String filmCertification = "";
        try {
            logStatus("getElementText", "Getting element text");

            // Retrieve the parent element
            WebElement element = getElement(driver, elementLocator);

            // Retrieve the child element and get its text content
            filmCertification = element.findElement(childLocator).getText();
        } catch (Exception e) {
            logStatus("getElementText", "Exception\n\t\t\t" + e.getMessage());
        }
        return filmCertification;
    }

    /**
     * Retrieves a list of WebElements identified by the specified locator within the WebDriver's current context.
     *
     * @param driver The WebDriver instance where the elements are located.
     * @param firstNNewsLocator The By locator used to identify the list of WebElements to retrieve.
     * @return A list of WebElements identified by the locator if found, otherwise an empty list.
     */
    public static List<WebElement> getElements(WebDriver driver, By firstNNewsLocator) {
        List<WebElement> elements = new ArrayList<>();
        try {
            logStatus("getElements", "Getting elements");

            // Find all elements matching the locator
            elements = driver.findElements(firstNNewsLocator);
        } catch (Exception e) {
            logStatus("getElements", "Exception\n\t\t\t" + e.getMessage());
        }
        return elements;
    }

    /**
     * Retrieves the body content and view count of news posts identified by the specified locator within the WebDriver's current context.
     *
     * @param driver The WebDriver instance where the news posts are located.
     * @param firstNNewsLocator The By locator used to identify the list of news posts.
     */
    public static void getBodyAndViewCount(WebDriver driver, By firstNNewsLocator) {
        int totalLikes = 0;
        try {
            logStatus("getBodyAndViewCount", "Getting body and view count");

            // Wait for the first news post to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstNNewsLocator));

            // Retrieve all news posts matching the provided locator
            List<WebElement> newsPosts = YouTubeUtils.getElements(driver, firstNNewsLocator);

            // Iterate through each news post
            for (WebElement elementBody : newsPosts) {
                int likes = 0;
                try {
                    // Retrieve the number of likes for the current news post
                    WebElement numberOfLikes = elementBody.findElement(By.xpath(".//span[contains(@id, 'vote-count-middle')]"));
                    likes = Integer.parseInt(numberOfLikes.getText());
                } catch (Exception e) {
                    // If likes count is not available or cannot be parsed, set likes to 0
                    likes = 0;
                }

                // Retrieve the body content of the current news post
                WebElement bodyElement = elementBody.findElement(By.xpath(".//div[contains(@id, 'body')]"));
                totalLikes += likes;

                logStatus("getBodyAndViewCount", String.format("%s Likes, Body: %s", likes, bodyElement.getText()));
            }
        } catch (Exception e) {
            logStatus("getBodyAndViewCount", "Exception\n\t\t\t" + e.getMessage());
        }
        logStatus("getBodyAndViewCount", "Total likes: " + totalLikes);
    }

    /**
     * Scrolls through video views until the total count reaches the specified target count.
     *
     * @param driver The WebDriver instance where the videos are located.
     * @param totalCount The target total count of video views to reach.
     */
    public static void scrollTillVideoCountReaches(WebDriver driver, long totalCount) {
        try {
            logStatus("scrollTillVideoCountReaches", "Scrolling till view count reaches " + totalCount);

            // Initialize the index for locating video views
            int index = 1;

            // Continue scrolling until the total count reaches 0 or below
            while (totalCount >= 0) {
                // Wait for the video view element to become visible
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                WebElement videoViews = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ytd-video-renderer//span[contains(text(), 'views')])["+ index +"]")));

                // Scroll the viewport to the video view element
                YouTubeUtils.scrollToViewport(driver, By.xpath("(//ytd-video-renderer//span[contains(text(), 'views')])["+ index +"]"));

                // Subtract the views count of the current video from the total count
                totalCount -= parseViewsCount(videoViews.getText());

                // Increment the index for the next video
                index++;
                logStatus("scrollTillVideoCountReaches", String.format("(%s) %s views, Remaining: %s", videoViews.getText(), parseViewsCount(videoViews.getText()), (totalCount <= 0) ? "Count reached" : totalCount));
            }
        } catch (Exception e) {
            logStatus("scrollTillVideoCountReaches", "Exception\n\t\t\t" + e.getMessage());
        }
    }

    /**
     * Parses the views count from the given text representation of views.
     *
     * @param viewsText The text representation of views to parse the count from.
     * @return The parsed views count as a long integer.
     */
    private static long parseViewsCount(String viewsText) {
        // Remove the " views" suffix from the views text
        viewsText = viewsText.replace(" views", "");
        long viewsCount;
        if (viewsText.endsWith("K")) {
            // Convert the abbreviated count with "K" suffix into a long integer
            viewsCount = (long) (Double.parseDouble(viewsText.substring(0, viewsText.length() - 1)) * 1_000);
        } else if (viewsText.endsWith("M")) {
            // Convert the abbreviated count with "M" suffix into a long integer
            viewsCount = (long) (Double.parseDouble(viewsText.substring(0, viewsText.length() - 1)) * 10_00_000);
        } else {
            // Convert the count without any suffix into a long integer
            viewsCount = Long.parseLong(viewsText);
        }
        return viewsCount;
    }
}
