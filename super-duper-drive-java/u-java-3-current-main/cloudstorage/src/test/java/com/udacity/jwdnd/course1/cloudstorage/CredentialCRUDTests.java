package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialCRUDTests {
    @Autowired
    private EncryptionService encryptionService;
    @LocalServerPort
    private int localport;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {

        this.driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 40);

        driver.get("http://localhost:" + this.localport + "/signup");
        WebElement firstName = driver.findElement(By.id("inputFirstName"));
        WebElement lastName = driver.findElement(By.id("inputLastName"));
        WebElement username = driver.findElement(By.id("inputUsername"));
        WebElement password = driver.findElement(By.id("inputPassword"));
        WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));

        firstName.sendKeys("bob");
        lastName.sendKeys("doe");
        username.sendKeys("user1");
        password.sendKeys("password");
        signUpButton.click();


        driver.get("http://localhost:" + this.localport + "/login");
        username = driver.findElement(By.id("inputUsername"));
        password = driver.findElement(By.id("inputPassword"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        username.sendKeys("user1");
        password.sendKeys("password");

        loginButton.click();
        Assertions.assertEquals("Home", driver.getTitle());
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    @Test
    public void credentialCreateTest() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement addNewCredential = driver.findElement(By.className("new-credential-btn"));
        WebElement credentialURL = driver.findElement(By.id("credential-url"));
        WebElement credentialUserName = driver.findElement(By.id("credential-username"));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialURL)).sendKeys("cnn.com");
        wait.until(ExpectedConditions.elementToBeClickable(credentialUserName)).sendKeys("user1");
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys("password1");
        WebElement credentialSaveBtn = driver.findElement(By.className("credentialSaveBtn"));
        credentialSaveBtn.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.localport + "/home");
        credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("th"));

        Optional<WebElement> plainPassword = credentialsList .stream()
                .filter(e -> e.getAttribute("innerHTML").equals("password1"))
                .findFirst();
        Optional<WebElement> newURL= credentialsList .stream()
                .filter(e -> e.getAttribute("innerHTML").equals("cnn.com"))
                .findFirst();

        Assertions.assertTrue(newURL.isPresent());
        Assertions.assertTrue(plainPassword.isEmpty());

}   @Test
    public void credentialUpdateTest() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement addNewCredential = driver.findElement(By.className("new-credential-btn"));
        WebElement credentialURL = driver.findElement(By.id("credential-url"));
        WebElement credentialUserName = driver.findElement(By.id("credential-username"));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialURL)).sendKeys("cnn.com");
        wait.until(ExpectedConditions.elementToBeClickable(credentialUserName)).sendKeys("user1");
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys("password1");
        WebElement credentialSaveBtn = driver.findElement(By.className("credentialSaveBtn"));
        credentialSaveBtn.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.localport + "/home");
        credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("td"));

        WebElement editBtn = credentialsList.stream()
                .map(element -> element.findElement(By.id("credentialEditBtn")))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        credentialURL = driver.findElement(By.id("credential-url"));
        wait.until(ExpectedConditions.elementToBeClickable(credentialURL));
        credentialURL.clear();
        credentialURL.sendKeys("apple.com");

        credentialSaveBtn = driver.findElement(By.className("credentialSaveBtn"));
        credentialSaveBtn.click();
        Assertions.assertEquals("Result", driver.getTitle());

        //check the updated
       driver.get("http://localhost:" + this.localport + "/home");
        credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(40));
        credentialsTable = driver.findElement(By.id("credentialTable"));
        credentialsList = credentialsTable.findElements(By.tagName("th"));

        boolean updated = credentialsList.stream()
                .anyMatch(element -> element.getAttribute("innerHTML").equals("apple.com"));
        Assertions.assertTrue(updated);

    }

    @Test
    public void credentialDeleteTest() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement addNewCredential = driver.findElement(By.className("new-credential-btn"));
        WebElement credentialURL = driver.findElement(By.id("credential-url"));
        WebElement credentialUserName = driver.findElement(By.id("credential-username"));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewCredential)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialURL)).sendKeys("cnn.com");
        wait.until(ExpectedConditions.elementToBeClickable(credentialUserName)).sendKeys("user1");
        wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys("password1");
        WebElement credentialSaveBtn = driver.findElement(By.className("credentialSaveBtn"));
        credentialSaveBtn.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.localport + "/home");
        credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("td"));

        WebElement deleteBtn = credentialsList.stream()
                .map(element -> element.findElement(By.className("credentialDeleteBtn")))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }

}
