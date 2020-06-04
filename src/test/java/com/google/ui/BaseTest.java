package com.google.ui;

import com.google.loglistener.LogListener;
import com.google.loglistener.RetryAnalyser;
import com.google.webdriver.DriverRepository;
import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.Arrays;

import static com.google.webdriver.DriverRepository.getBrowserType;

@Listeners(LogListener.class)
public class BaseTest {

    private final DriverRepository driverRepository = new DriverRepository();

    @BeforeSuite
    public void downloadDriver(ITestContext iTestContext) {
       for (ITestNGMethod method : iTestContext.getAllTestMethods()) {
           method.setRetryAnalyzerClass(RetryAnalyser.class);
       }
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
