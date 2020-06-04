package com.google.pageobgect.initialpage;

import com.google.pageobgect.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(xpath = ".//*[@title='Search' or @title='Пошук']")
    private WebElement searchInputField;

    @FindBy(xpath = ".//*[@type='submit' and contains(@value, 'Google')]")
    private List<WebElement> submitButton;

    @FindBy(xpath = ".//*[@aria-label='Транслітерація' or @aria-label='Transliteration']")
    private List<WebElement> transliterationButton;

    public WebElement getSearchInputField() {
        return searchInputField;
    }

    public List<WebElement> getSubmitButton() {
        return submitButton;
    }

    public List<WebElement> getTransliterationButton() {
        return transliterationButton;
    }

    public List<WebElement> getInputValue(String value) {
        return driver.findElements(By.xpath(".//*[@value='" + value + "' and not(contains(@type,'hidden'))]"));
    }
}
