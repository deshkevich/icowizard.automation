package org.oracles.wizard.autotests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class PrototypeTest {

    ChromeDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("MetaMask_v3.12.1.crx"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void mainFlow() {

        driver.get("chrome-extension://nkbihfbeogaeaoehlefnkodbefgpgknn/popup.html");
        driver.findElement(By.xpath("//button[text()='Accept']")).click();
        WebElement element = driver.findElement(By.xpath("//a[@href='https://metamask.io/terms.html']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        driver.findElement(By.xpath("//button[text()='Accept']")).click();

        driver.get("https://wizard.oracles.org/");
        driver.findElement(By.xpath("//a[text()='New crowdsale']")).click();
        driver.findElement(By.xpath("//a[text()='Continue']")).click();
        driver.findElement(By.xpath("//*[@id='root']/div/section/div[2]/div[2]/div[1]/input")).sendKeys("name");
        driver.findElement(By.xpath("//*[@id='root']/div/section/div[2]/div[2]/div[2]/input")).sendKeys("123");
        driver.findElement(By.xpath("//*[@id='root']/div/section/div[2]/div[2]/div[3]/input")).sendKeys("1");
        driver.findElement(By.xpath("//a[text()='Continue']")).click();
    }

    @AfterClass
    public void tearDown() {
        driver.close();
    }
}
