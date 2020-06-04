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

    @Step("Expand transliteration")
    public VisualKeyboardUIBL expandTransliteration() {
        searchPage.getTransliterationButton().get(0).click();
        return new VisualKeyboardUIBL();
    }

    @Step("Check that inputed value correct")
    public boolean isInputFieldValueCorrect(String value) {
        return !searchPage.getInputValue(value).isEmpty();
    }

    @Step("Press Search button")
    public SearchResultUIBL pressSearchButton() {
        try {
            new DriverUtil().clickOnElementJS(searchPage.getSubmitButton().get(1));
        } catch (ElementNotInteractableException ex) {
            new DriverUtil().clickOnElementJS(searchPage.getSubmitButton().get(1));
        }
        return new SearchResultUIBL();
    }

    @Step("Check that translitration button displayed")
    public boolean isTransliterationButtonDisplayed() {
        return !searchPage.getTransliterationButton().isEmpty();
    }
}
