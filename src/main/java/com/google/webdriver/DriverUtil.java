package com.google.webdriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DriverUtil {

    private WebDriver driver;
    private JavascriptExecutor javaScriptExecutor;


    public DriverUtil() {
        driver = DriverRepository.DRIVERS.get();
        javaScriptExecutor = (JavascriptExecutor) driver;
    }

    public String getDocumentState() {
        return (String) javaScriptExecutor.executeScript("return document.readyState");
    }

    public void clickOnElementJS(WebElement webElement) {
        javaScriptExecutor.executeScript("arguments[0].click();", webElement);
    }
}
