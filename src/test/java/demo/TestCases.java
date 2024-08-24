package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.Reporter;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
//import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);
                driver.get("https://www.youtube.com/");

                driver.manage().window().maximize();
        }

        @Test(enabled = false)
        public void testCase01() {
                Reporter.log("Start Test case: testCase01");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                String expectedURL = "https://www.youtube.com/";
                String actualURL = driver.getCurrentUrl();
                Assert.assertEquals(actualURL, expectedURL, "Invalid URL");
                WebElement abouElement = driver.findElement(By.xpath("//a[normalize-space()='About']"));
                Wrappers.clickOnElement(driver, abouElement);
                wait.until(ExpectedConditions.urlContains("about"));
                String message = driver.findElement(By.cssSelector("section:nth-child(1) > p:nth-child(2)")).getText();
                Assert.assertEquals(message, "Our mission is to give everyone a voice and show them the world.",
                                "Actual message and Expected message not same");
                Reporter.log("end Test case: testCase01");
        }

        @Test
        public void testCase02() throws InterruptedException {
                Reporter.log("Start Test case: testCase02");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                WebElement Movies = wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//yt-formatted-string[normalize-space()='Movies']")));
                Wrappers.clickOnElement(driver, Movies);

                WebElement rightArrow = driver.findElement(By.xpath("(//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button)[1]"));

                while (Wrappers.checkButtonVisible(rightArrow)) {
                        Wrappers.clickOnElement(driver, rightArrow);
                }
        }

        @AfterTest
        public void endTest() {
                // driver.close();
                driver.quit();

        }
}