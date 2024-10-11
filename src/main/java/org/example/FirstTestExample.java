package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class FirstTestExample {

    WebDriver wd;
    WebDriverWait wait;

    @BeforeTest()
    public void beforeTestFixture() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");

        wd = WebDriverManager
                .chromedriver()
                .capabilities(chromeOptions)
                .create();
    }

    @Test()
    public void firstTest() {
        wd.get("https://rkn.gov.ru/");
        wd.manage().window().maximize();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException i) {
            i.printStackTrace(System.err);
        }

        String title = wd.getTitle();
        Assert.assertTrue(title.contains("Роскомнадзор"), "Title should contain 'Роскомнадзор'");

        WebElement searchIcon = wd.findElement(By.className("header-menu-search-icon"));
        searchIcon.click();

        WebElement searchBox = wd.findElement(By.className("header-menu-search-input"));
        searchBox.sendKeys("Discord");

        WebElement search = wd.findElement(By.name("search"));

        Assert.assertEquals(search.getAttribute("value"), "Discord", "Search box should contain the correct text");

        searchBox.submit();

        WebElement formControl = wd.findElement(By.className("form-control"));

        Assert.assertTrue(formControl.isDisplayed(), "Form control should be visible after search");
        Assert.assertTrue(formControl.isEnabled(), "Form control should be enabled after search");

        boolean isSearchBlocked = wd.findElements(By.cssSelector(".search-results a")).isEmpty();
        Assert.assertTrue(isSearchBlocked, "Search results should not be accessible before passing the captcha");
    }
}
