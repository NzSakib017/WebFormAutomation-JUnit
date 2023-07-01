import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebFormAssertion {
    WebDriver driver;
    WebDriverWait explicitWait;
    @BeforeAll
    public void environmentSetup(){

//        driver = new FirefoxDriver(new FirefoxOptions().addArguments("-private"));                                    //Firefox Private Browsing
//        driver = new ChromeDriver(new ChromeOptions().addArguments("incognito"));                                     //Google Chrome Incognito

        driver = new ChromeDriver();                                                                                    //Normal Chrome Window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.findElement(By.id("onetrust-reject-all-handler")).click();
//        delayedElementHandler(By.cssSelector(".spb-popup-main-wrapper"),30);
//        driver.findElement(By.cssSelector(".block-digitalunitepopup-modal-close.spb_close")).click();
    }

    public void delayedElementHandler(By WebElement, int second){
        explicitWait = new WebDriverWait(driver,Duration.ofSeconds(second));
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(WebElement));
    }

    @DisplayName("Test Webpage Loading")
    @Test
    public void getTitle() {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        String receivedTitle = driver.getTitle();
        System.out.println(receivedTitle);
        Assertions.assertTrue(receivedTitle.contains("Digital Unite"));
//        Assertions.assertEquals(receivedTitle,"Digital Unite");
    }

    @DisplayName("Web Form Input & Submit")
    @Test
    public void formSubmission() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        delayedElementHandler(By.cssSelector(".spb-popup-main-wrapper"),30);
        boolean popupFound = driver.findElement(By.cssSelector(".spb-popup-main-wrapper")).isDisplayed();

        if (popupFound)
            driver.findElement(By.cssSelector(".block-digitalunitepopup-modal-close")).click();
        else
        {
            List<WebElement> textBoxElement = new ArrayList<WebElement>();
            textBoxElement.add(driver.findElement(By.id("edit-name")));
            textBoxElement.add(driver.findElement(By.id("edit-number")));
            textBoxElement.add(driver.findElement(By.id("edit-email")));
            textBoxElement.add(driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-")));

            textBoxElement.get(0).sendKeys("Sakib");
            Thread.sleep(2000);
            textBoxElement.get(1).sendKeys("+8801719637539");
            Thread.sleep(500);

            driver.findElement(By.cssSelector("label[for = 'edit-agnew-30-40']")).click();                              //AGE Button Click
            Thread.sleep(2000);

            driver.findElement(By.id("edit-date")).click();
            driver.findElement(By.id("edit-date")).sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
            String currentDate = new SimpleDateFormat("ddMMMyyyy").format(new Date());
            driver.findElement(By.id("edit-date")).sendKeys(currentDate + Keys.ARROW_RIGHT + currentDate.substring(5));
            Thread.sleep(2000);

            textBoxElement.get(2).sendKeys("testwebform@junit.com");
            Thread.sleep(2000);
            textBoxElement.get(3).sendKeys("This a web form submission automation testing.");
            Thread.sleep(3000);

            driver.findElement(By.id("edit-uploadocument-upload")).sendKeys("C:\\Users\\nzsak\\Desktop\\WebFormAutomationFile.xlsx");
            Thread.sleep(10000);

            driver.findElement(By.id("edit-age")).click();                                                              //Check Box Button Click
        }


        driver.findElement(By.id("edit-submit")).click();                                                               //SUBMIT Button Click

        String confirmationMessage = driver.findElement(By.xpath("//h1[contains(text(),'Thank you for your submission!')]")).getText();
        Assertions.assertEquals(confirmationMessage,"Thank you for your submission!");

    }

    @AfterAll
    public void closeDrive(){
        driver.close();
    }
}
