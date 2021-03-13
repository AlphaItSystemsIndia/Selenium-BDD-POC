package com.amazon.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Selenium {
    static {
        // Configure chrome web driver file path
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + ProjectProperties.getChromeDriverFilePath());
    }

    private static WebDriver driver;

    public static void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        // Start chrome driver in headless mode
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    public static void quitBrowser() {
        if (driver != null) driver.quit();
    }

    public static WebDriver browser() {
        if (driver == null) throw new IllegalStateException("Accessing browser without invoking startBrowser() first");
        return driver;
    }
}
