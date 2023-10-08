import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebFormAssertion {
    WebDriver driver;
    WebDriverWait explicitWait;
    @BeforeAll
    public void environmentSetup() {
        driver = getBrowserDriver("chrome", false);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.findElement(By.id("onetrust-reject-all-handler")).click();
    }

    private WebDriver getBrowserDriver(final String browserName, final boolean inPrivate) {
        switch(browserName) {
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (inPrivate) {
                    firefoxOptions.addArguments("-private");
                }
                return new FirefoxDriver(firefoxOptions);
            }
            default: {
                ChromeOptions chromeOptions = new ChromeOptions();
                if (inPrivate) {
                    chromeOptions.addArguments("incognito");
                }
                return new ChromeDriver(chromeOptions);
            }
        }
    }

    @DisplayName("Test Webpage Loading")
    @Test
    public void testTitle() {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        String receivedTitle = driver.getTitle();
        System.out.println(receivedTitle);
        Assertions.assertTrue(receivedTitle.contains("Digital Unite"));
    }

    @DisplayName("Web Form Input & Submit")
    @Test
    public void testFormSubmission() throws InterruptedException, URISyntaxException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        if (isPopupFound(30))
            findElementByCssSelector(".block-digitalunitepopup-modal-close").click();

        updateValueOfElement(findElementById("edit-name"), "Sakib", 2000);
        updateValueOfElement(findElementById("edit-number"), "+8801719637539", 500);

        findElementByCssSelector("label[for = 'edit-agnew-30-40']").click();
        Thread.sleep(2000);

        findElementById("edit-date").click();
        updateValueOfElement(findElementById("edit-date"), Keys.CONTROL + "A" + Keys.DELETE);
        String currentDate = new SimpleDateFormat("ddMMMyyyy").format(new Date());
        updateValueOfElement(findElementById("edit-date"), currentDate + Keys.ARROW_RIGHT + currentDate.substring(5), 2000);

        updateValueOfElement(findElementById("edit-email"), "testwebform@junit.com", 2000);
        updateValueOfElement(findElementById("edit-tell-us-a-bit-about-yourself-"),"This a web form submission automation testing.", 3000);

        WebElement submitButton = findElementById("edit-submit");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", submitButton);

        URL resource = WebFormAssertion.class.getResource("WebFormAutomationFile.xlsx");
        File file = new File(resource.toURI());
        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys(file.getAbsolutePath());
        Thread.sleep(10000);

        WebElement completionCheckbox = findElementById("edit-age");
        completionCheckbox.click();
        submitButton.click();

        String confirmationMessage = driver.findElement(By.xpath("//h1[contains(text(),'Thank you for your submission!')]")).getText();
        Assertions.assertEquals(confirmationMessage,"Thank you for your submission!");
    }

    private boolean isPopupFound(int second) {
        explicitWait = new WebDriverWait(driver,Duration.ofSeconds(second));
        By webElement = By.cssSelector(".spb-popup-main-wrapper");

        try {
            explicitWait.until(ExpectedConditions.presenceOfElementLocated(webElement));
            return driver.findElement(webElement).isDisplayed();
        } catch (NoSuchElementException | TimeoutException noSuchElementException) {
            return false;
        }
    }

    private WebElement findElementById(final String id) {
        return driver.findElement(By.id(id));
    }

    private WebElement findElementByCssSelector(final String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    private void updateValueOfElement(final WebElement element, final String value, final int delay) throws InterruptedException {
        updateValueOfElement(element, value);
        Thread.sleep(delay);
    }

    private void updateValueOfElement(final WebElement element, final String value) {
        element.sendKeys(value);
    }

    @AfterAll
    public void closeDrive(){
        driver.close();
    }
}
