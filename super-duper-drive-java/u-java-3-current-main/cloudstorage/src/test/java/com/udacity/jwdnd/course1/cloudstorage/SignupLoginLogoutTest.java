package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupLoginLogoutTest {
    @LocalServerPort
    private int localport;
    private WebDriver webdriver;
    @Test
    public void TestSignupLoginLogout() {
        WebDriverManager.chromedriver().setup();
        this.webdriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webdriver, 40);

        //unauth user
        webdriver.get("http://localhost:" + this.localport + "/home");
        Assertions.assertEquals("Login", webdriver.getTitle());
        webdriver.get("http://localhost:" + this.localport + "/login");
        Assertions.assertEquals("Login", webdriver.getTitle());
        webdriver.get("http://localhost:" + this.localport + "/signup");
        Assertions.assertEquals("Sign Up", webdriver.getTitle());


        //auth user
        webdriver.get("http://localhost:" + this.localport + "/signup");
        WebElement firstName = webdriver.findElement(By.id("inputFirstName"));
        WebElement lastName = webdriver.findElement(By.id("inputLastName"));
        WebElement username = webdriver.findElement(By.id("inputUsername"));
        WebElement password = webdriver.findElement(By.id("inputPassword"));
        WebElement signUpButton = webdriver.findElement(By.id("buttonSignUp"));


        firstName.sendKeys("bob");
        lastName.sendKeys("doe");
        username.sendKeys("user1");
        password.sendKeys("password");
        signUpButton.click();

        webdriver.get("http://localhost:" + this.localport + "/login");
        username = webdriver.findElement(By.id("inputUsername"));
        password = webdriver.findElement(By.id("inputPassword"));
        WebElement loginButton = webdriver.findElement(By.id("login-button"));

        username.sendKeys("user1");
        password.sendKeys("password");
        loginButton.click();
        Assertions.assertEquals("Home", webdriver.getTitle());

        WebElement logoutButton = webdriver.findElement(By.id("logout-btn"));
        logoutButton.click();
        Assertions.assertEquals("Login", webdriver.getTitle());

        webdriver.get("http://localhost:" + this.localport + "/home");
        Assertions.assertEquals("Login", webdriver.getTitle());
        if (this.webdriver != null) {
            webdriver.quit();
        }
    }
}
