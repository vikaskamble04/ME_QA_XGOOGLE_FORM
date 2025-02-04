package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
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

        driver.manage().window().maximize();
    }
    @Test
    public void testCase01() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 

        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");

        
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='text'])[1]")));
        nameField.sendKeys("Crio Learner");
        long epochTime = Instant.now().getEpochSecond();
        WebElement textArea = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@jsname='YPqjbf']")));
        textArea.sendKeys("I want to be the best QA Engineer! "+epochTime);

        
        WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'AB7Lab')])[2]")));
        radioButton.click();

     
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'uHMk6b')])[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'uHMk6b')])[2]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'uHMk6b')])[4]"))).click();

        
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Choose']")));
        Actions action = new Actions(driver);
        action.moveToElement(dropdown).click().perform();
        
        WebElement select = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@data-value='Mr'])[2]")));
        action.moveToElement(select).doubleClick().perform();
        Thread.sleep(2000);
     
        LocalDate dateMinus7Days = LocalDate.now().minusDays(7);
        String date = dateMinus7Days.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='date' and @max='2075-01-01']")));
        dateField.sendKeys(date);

       
        LocalTime currentTime = LocalTime.now();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Hour']"))).sendKeys(String.valueOf(currentTime.getHour()));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Minute']"))).sendKeys(String.valueOf(currentTime.getMinute()));

    
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Submit']")));
        submitButton.click();


        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("vHW8K")));
        System.out.println(successMessage.getText());

        WebElement submitAnotherFormLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[4]/a")));
        System.out.println(submitAnotherFormLink.getText());
        System.out.println("Test case passed successfully!");
    }

     @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}