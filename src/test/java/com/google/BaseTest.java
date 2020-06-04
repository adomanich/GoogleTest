package com.google;

import com.google.webdriver.DriverRepository;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static com.google.webdriver.DriverRepository.getBrowserType;

public class BaseTest {

    private final DriverRepository driverRepository = new DriverRepository();

    @BeforeSuite
    public void downloadDriver() {
        driverRepository.downloadWebDriver();
    }

    @BeforeClass
    public void instantiateBrowser() {
        driverRepository.instantiateBrowser();
    }

    @AfterClass
    public void shutDown() {
        if (getBrowserType().equals(BrowserType.CHROME)) {
            DriverRepository.DRIVERS.get().manage().deleteAllCookies();
        }
        DriverRepository.DRIVERS.get().quit();
    }
}
