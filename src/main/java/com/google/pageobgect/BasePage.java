package com.google.pageobgect;

import com.google.webdriver.DriverUtil;
import com.google.webdriver.DriverRepository;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.google.constant.TestDataValues.WAIT_VALUE;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        driver = DriverRepository.DRIVERS.get();
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_VALUE));
        var driverUtil = new DriverUtil();
        wait.until(webDriver -> driverUtil.getDocumentState().equals("complete"));
        PageFactory.initElements(driver, this);
    }
}
