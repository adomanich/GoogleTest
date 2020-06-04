package com.google.business.initial;

import com.google.business.SearchResultUIBL;
import com.google.pageobgect.initialpage.SearchPage;
import com.google.webdriver.DriverUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementNotInteractableException;

public class SearchUIBL {

    private SearchPage searchPage;

    public SearchUIBL() {
        searchPage = new SearchPage();
    }

    @Step("Fill into search input field")
    public SearchUIBL fillInSearchInput(String searchValue) {
        searchPage.getSearchInputField().sendKeys(searchValue);
        return this;
    }

    @Step("Expand visual keyboard")
    public VisualKeyboardUIBL expandVisualKeyboard() {
        searchPage.getVisualKeyboardButton().get(0).click();
        return new VisualKeyboardUIBL();
    }

    @Step("Check that input value is correct")
    public boolean isInputFieldValueCorrect(String value) {
        return !searchPage.getInputValue(value).isEmpty();
    }

    @Step("Click on search button")
    public SearchResultUIBL pressSearchButton() {
        try {
            new DriverUtil().clickOnElementJS(searchPage.getSubmitButton().get(0));
        } catch (ElementNotInteractableException ex) {
            new DriverUtil().clickOnElementJS(searchPage.getSubmitButton().get(1));
        }
        return new SearchResultUIBL();
    }

    @Step("Check that visual keyboard button displayed")
    public boolean isTransliterationButtonDisplayed() {
        return !searchPage.getVisualKeyboardButton().isEmpty();
    }
}
