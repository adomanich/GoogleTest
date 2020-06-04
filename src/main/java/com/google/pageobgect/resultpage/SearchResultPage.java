package com.google.pageobgect.resultpage;

import com.google.pageobgect.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultPage extends BasePage {

    @FindBy(xpath = ".//*[@id='search']//*[@class='g']")
    private List<WebElement> resultList;

    @FindBy(xpath = ".//*[@class='med card-section']/*[@role='heading']")
    private List<WebElement> incorrectSearchResult;

    public SearchResultPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("res")));
    }

    public List<WebElement> getResultList() {
        return resultList;
    }

    public List<WebElement> getIncorrectSearchResult() {
        return incorrectSearchResult;
    }
}
