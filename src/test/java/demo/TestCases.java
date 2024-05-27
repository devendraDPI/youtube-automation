package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.utils.ActionsWrapper;
import demo.utils.ExcelDP;
import demo.utils.YouTubeUtils;

public class TestCases {
    WebDriver driver;
    SoftAssert sa = new SoftAssert();

    @BeforeClass
    public void createDriver() {
        YouTubeUtils.logStatus("createDriver", "Creating driver");

        // Setting up ChromeOptions with desired arguments
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Creating a Chrome WebDriver instance with the specified options
        driver = new ChromeDriver(options);

        YouTubeUtils.logStatus("createDriver", "Done driver creation");
    }

    @AfterClass
    public void quitDriver() {
        YouTubeUtils.logStatus("quitDriver", "Quitting driver");
        // Quit the WebDriver instance
        if (driver != null) {
            driver.quit();
        }
        YouTubeUtils.logStatus("quitDriver", "Done driver quit");
    }

    @BeforeMethod
    public void driverGet() {
        // Navigate to https://www.youtube.com
        driver.get("https://www.youtube.com");
    }

    @Test(priority = 1, enabled = true, description = "Verify youtube url and print about page message")
    public void TestCase001() {
        YouTubeUtils.logStatus("TC001", "Start", "Verify youtube url and print about page message");
        // Assert current URL contains "youtube"
        String urlToContain = "youtube";
        Boolean status = YouTubeUtils.verifyCurrentUrlContains(driver, urlToContain);
        Assert.assertTrue(status, "Current url does not contain " + urlToContain);

        // Locate the About link at the bottom of the sidebar and Click on "About"
        By aboutLocator = By.xpath("//a[contains(text(), 'About')]");
        YouTubeUtils.scrollToViewport(driver, aboutLocator);
        ActionsWrapper.clickAW(driver, aboutLocator);

        // Print the message displayed on the screen
        By messageLocator = By.xpath("//section[contains(@class, 'about__content')]");
        YouTubeUtils.scrollToViewport(driver, messageLocator);
        YouTubeUtils.getDisplayedMessage(driver, messageLocator);
        YouTubeUtils.logStatus("TC001", "End", "Verify youtube url and print about page message");
    }

    @Test(priority = 2, enabled = true, description = "Verify movie is marked 'A' for Mature and movie is either 'Comedy' or 'Animation'")
    public void TestCase002() {
        YouTubeUtils.logStatus("TC002", "Start", "Verify movie is marked 'A' for Mature and movie is either 'Comedy' or 'Animation'");
        // Navigate to the "Films" tab
        By filmsTab = By.xpath("//a[contains(@title, 'Films') or contains(@title, 'Movies')]");
        YouTubeUtils.scrollToViewport(driver, filmsTab);
        ActionsWrapper.clickAW(driver, filmsTab);

        // Scroll to the extreme right within the "Top Selling" section
        String section = "Top selling";
        YouTubeUtils.scrollToExtreme(driver, By.xpath("//span[contains(text(), '"+ section +"')]/ancestor::div[contains(@class, 'item-section')]//button[contains(@aria-label, 'Next')]"));

        // Last movie
        By lastMovie = By.xpath("(//span[contains(text(), '"+ section +"')]/ancestor::div[contains(@class, 'item-section')]//ytd-grid-movie-renderer)[last()]");

        // Soft Assert on whether the movie is marked "A" for Mature or not
        By filmCertificationLocator = By.xpath(".//p[not(contains(text(), 'Buy') or contains(text(), 'Rent'))]");
        String filmCertification = YouTubeUtils.getElementText(driver, lastMovie, filmCertificationLocator);
        sa.assertEquals(filmCertification, "A", "The movie certification is not marked as 'A'");

        // Soft assert on whether the movie is either "Comedy" or "Animation"
        By movieGenreLocator = By.xpath(".//span[contains(@class, 'metadata')]");
        String movieGenre = YouTubeUtils.getElementText(driver, lastMovie, movieGenreLocator);
        sa.assertTrue(movieGenre.contains("Comedy") || movieGenre.contains("Animation"), "The movie genre is neither 'Comedy' nor 'Animation'");

        sa.assertAll();
        YouTubeUtils.logStatus("TC002", "End", "Verify movie is marked 'A' for Mature and movie is either 'Comedy' or 'Animation'");
    }

    @Test(priority = 2, enabled = true, description = "Verify number of tracks is 50 or less")
    public void TestCase003() {
        YouTubeUtils.logStatus("TC003", "Start", "Verify number of tracks is 50 or less");
        // Navigate to the "Music" tab of the application
        By musicTab = By.xpath("//a[contains(@title, 'Music')]");
        YouTubeUtils.scrollToViewport(driver, musicTab);
        ActionsWrapper.clickAW(driver, musicTab);

        // Locate the first section of playlists
        By firstSection = By.xpath("(//ytd-item-section-renderer)[1]");
        YouTubeUtils.scrollToViewport(driver, firstSection);

        // Scroll to the extreme right within the first section
        YouTubeUtils.scrollToExtreme(driver, By.xpath("(//ytd-item-section-renderer)[1]//button[contains(@aria-label, 'Next')]"));

        // Last playlist
        By lastPlaylist = By.xpath("((//ytd-item-section-renderer)[1]//ytd-compact-station-renderer)[last()]");

        // Print the name of the playlist
        By playlistNameLocator = By.xpath(".//h3");
        String playlistName = YouTubeUtils.getElementText(driver, lastPlaylist, playlistNameLocator);
        YouTubeUtils.logStatus("TC003", "Step", "Playlist name: " + playlistName);

        // Count the number of tracks listed in the playlist
        By numberOfTrackLocator = By.xpath(".//p[contains(@id, 'video-count')]");
        int numberOfTracks = Integer.parseInt(YouTubeUtils.getElementText(driver, lastPlaylist, numberOfTrackLocator).replaceAll("[\\D]", ""));
        YouTubeUtils.logStatus("TC003", "Step", "Track Count: " + numberOfTracks);

        // Soft assert whether the number of tracks listed is less than or equal to 50
        sa.assertTrue((numberOfTracks <= 50), "The number of tracks listed is more than 50");

        sa.assertAll();
        YouTubeUtils.logStatus("TC003", "End", "Verify number of tracks is 50 or less");
    }

    @Test(priority = 1, enabled = true, description = "Verify news body and likes")
    public void TestCase004() {
        YouTubeUtils.logStatus("TC004", "Start", "Verify news body and likes");
        // Navigate to the "News" tab of the application
        By newsTab = By.xpath("//a[contains(@title, 'News')]");
        YouTubeUtils.scrollToViewport(driver, newsTab);
        ActionsWrapper.clickAW(driver, newsTab);

        // Locate the "Latest News Posts" section
        By latestNewsPosts = By.xpath("//span[contains(text(), 'Latest news post')]");
        YouTubeUtils.scrollToViewport(driver, latestNewsPosts);

        // Retrieve and print the body and the number of likes for each of the first 3 news posts
        int numberOfNewsPosts = 3;
        By firstNNewsLocator = By.xpath("(//span[contains(text(), 'Latest news post')]/ancestor::ytd-rich-section-renderer//ytd-post-renderer)[position() <= "+ numberOfNewsPosts +"]");
        YouTubeUtils.getBodyAndViewCount(driver, firstNNewsLocator);
        YouTubeUtils.logStatus("TC004", "End", "Verify news body and likes");
    }

    @Test(priority = 1, enabled = true, description = "Verify video views count", dataProvider = "searchTerms", dataProviderClass = ExcelDP.class)
    public void TestCase005(String searchTerms) {
        YouTubeUtils.logStatus("TC005", "Start", "Verify video views count: " + searchTerms);
        // Search for the item
        By searchBox = By.xpath("//input[contains(@id, 'search')]");
        ActionsWrapper.sendKeysAW(driver, searchBox, searchTerms);

        // Scroll through the search results until the total views for the videos reach 10 crore
        long totalCount = 10_00_00_000;
        YouTubeUtils.scrollTillVideoCountReaches(driver, totalCount);
        YouTubeUtils.logStatus("TC005", "End", "Verify video views count: " + searchTerms);
    }
}
