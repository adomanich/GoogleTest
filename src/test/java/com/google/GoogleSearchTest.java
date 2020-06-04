package com.google;

import com.google.webdriver.DriverRepository;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class GoogleSearchTest extends BaseTest {


    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify if Google search is correct then search by typing")
    public void searchByTypint() {
        DriverRepository.DRIVERS.get().get("https://google.com");
        System.out.println();
    }
}
