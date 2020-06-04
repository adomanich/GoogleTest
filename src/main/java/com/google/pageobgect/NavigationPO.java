package com.google.pageobgect;

import com.google.listener.LogListener;
import com.google.webdriver.DriverRepository;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class NavigationPO {

    private WebDriver driver;
    private int browserWidth;
    private int browserHeight;
    private static final String GOOGLE_URL = "https://google.com";

    public NavigationPO() {
        initialiseDriver();
        browserWidth = 1440;
        browserHeight = 900;
    }

    public NavigationPO(int browserWidth, int browserHeight) {
        initialiseDriver();
        this.browserWidth = browserWidth;
        this.browserHeight = browserHeight;
    }

    private void initialiseDriver() {
        this.driver = DriverRepository.DRIVERS.get();
    }

    public void navigateToGoogle(String localisation) {
        driver.get(GOOGLE_URL + "?hl=" + localisation);
        LogListener.logger.info("Navigate to url: {}", GOOGLE_URL);
        driver.manage().window().setSize(new Dimension(browserWidth, browserHeight));
    }
}
