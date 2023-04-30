package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteCrudTests {
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
    public void noteCreateTest() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement addNewNoteBtn = driver.findElement(By.id("addNewNote"));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        WebElement savechangesBtn = driver.findElement(By.id("note-save-changes-btn"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys("Today to do");
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys("Things to do");
        wait.until(ExpectedConditions.elementToBeClickable(savechangesBtn)).click();
        Assertions.assertEquals("Result", driver.getTitle());

        //check for note
        driver.get("http://localhost:" + this.localport + "/home");
        notesTab = driver.findElement(By.id("nav-notes"));
        js.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("noteTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));

        Optional<WebElement> addedTitle = notesList.stream()
                .filter(e -> e.getAttribute("innerHTML").equals("Today to do"))
                .findFirst();
        boolean newNote = addedTitle.isPresent();
        Assertions.assertTrue(newNote);
    }
    @Test
    public void noteUpdateTest() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement addNewNoteBtn = driver.findElement(By.id("addNewNote"));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        WebElement savechangesBtn = driver.findElement(By.id("note-save-changes-btn"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys("Today to do");
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys("Things to do");
        wait.until(ExpectedConditions.elementToBeClickable(savechangesBtn)).click();
        Assertions.assertEquals("Result", driver.getTitle());

        //update note
        driver.get("http://localhost:" + this.localport + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement notesTable = driver.findElement(By.id("noteTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement editBtn = notesList.stream()
                .map(element -> element.findElement(By.id("note-edit-btn")))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        WebElement notetitle = driver.findElement(By.id("note-title"));
        wait.until(ExpectedConditions.elementToBeClickable(notetitle));
        notetitle.clear();
        notetitle.sendKeys("editing note title");
        savechangesBtn = driver.findElement(By.id("note-save-changes-btn"));
        savechangesBtn.click();
        Assertions.assertEquals("Result", driver.getTitle());

        //check the updated note
        driver.get("http://localhost:" + this.localport + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        notesTable = driver.findElement(By.id("noteTable"));
        notesList = notesTable.findElements(By.tagName("th"));

        boolean updated = notesList.stream()
                .anyMatch(element -> element.getAttribute("innerHTML").equals("editing note title"));
        Assertions.assertTrue(updated);

    }

    @Test
    public void noteDeleteTest() {
        WebDriverWait wait = new WebDriverWait(driver, 40);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement addNewNoteBtn = driver.findElement(By.id("addNewNote"));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        WebElement savechangesBtn = driver.findElement(By.id("note-save-changes-btn"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys("Today to do");
        wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).sendKeys("Things to do");
        wait.until(ExpectedConditions.elementToBeClickable(savechangesBtn)).click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.localport + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(40));
        WebElement notesTable = driver.findElement(By.id("noteTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement deleteBtn = notesList.stream()
                .map(element -> element.findElement(By.id("note-delete-btn")))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.localport + "/home");
        notesTab = driver.findElement(By.id("nav-credentials-tab"));
        js.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(40));
        notesTable = driver.findElement(By.id("noteTable"));
        notesList = notesTable.findElements(By.tagName("th"));

        boolean oldNote = notesList.stream()
                .anyMatch(element -> element.getAttribute("innerHTML").equals("Today to do"));
        Assertions.assertFalse(oldNote);



    }
}
