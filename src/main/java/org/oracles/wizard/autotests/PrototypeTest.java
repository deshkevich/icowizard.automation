package org.oracles.wizard.autotests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class PrototypeTest {

    ChromeDriver driver;

    private final static String METAMASK_PASSWORD = "12345678";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("MetaMask_v3.12.1.crx"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
    }

    @Test
    public void mainFlow() {
        driver.get("chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/popup.html");
        driver.findElement(By.xpath("//button[text()='Accept']")).click();
        WebElement element = driver.findElement(By.xpath("//a[@href='https://metamask.io/terms.html']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.xpath("//button[text()='Accept']")).click();
        driver.findElement(By.xpath("//*[@id='password-box']")).sendKeys(METAMASK_PASSWORD);
        driver.findElement(By.xpath("//*[@id='password-box-confirm']")).sendKeys(METAMASK_PASSWORD);
        driver.findElement(By.xpath("//button[text()='Create']")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'copied it somewhere safe')]")).click();

        driver.get("https://wizard.oracles.org/");
        driver.findElement(By.xpath("//a[text()='New crowdsale']")).click();
        driver.findElement(By.xpath("//span[text()='Continue']")).click();
        driver.findElement(By.xpath("//label[text()='Name']/following-sibling::input")).sendKeys("name1");
        driver.findElement(By.xpath("//label[text()='Ticker']/following-sibling::input")).sendKeys("100");
        driver.findElement(By.xpath("//label[text()='Decimals']/following-sibling::input")).sendKeys("13");
        driver.findElement(By.xpath("//a[text()='Continue']")).click();
        driver.findElement(By.xpath("//label[text()='Rate']/following-sibling::input")).sendKeys("12");
        driver.findElement(By.xpath("//label[text()='Supply']/following-sibling::input")).sendKeys("11");
        driver.findElement(By.xpath("//a[text()='Continue']")).click();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/popup.html");
        driver.findElement(By.xpath("//button[text()='Reject']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()=' (Rejected)']")).isDisplayed());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
