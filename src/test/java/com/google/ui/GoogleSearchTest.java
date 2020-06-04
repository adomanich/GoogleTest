package com.google.ui;

import com.google.assertion.VisualKeyboardAssertion;
import com.google.business.SearchResultUIBL;
import com.google.business.initial.SearchUIBL;
import com.google.business.initial.VisualKeyboardUIBL;
import com.google.pageobgect.NavigationPO;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.constant.TestDataValues.EN_LOCALISATION;
import static com.google.constant.TestDataValues.UK_LOCALISATION;

public class GoogleSearchTest extends BaseTest {

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify if Google search is correct then search using visual keyboard", groups = "regression")
    public void searchByTransliteration() {
        new NavigationPO().navigateToGoogle(UK_LOCALISATION);
        var searchUIBL = new SearchUIBL();
        String searchRequest = "Ютуб вежа";
        Assert.assertTrue(searchUIBL.isTransliterationButtonDisplayed(), "Error - transliteration button is not displayed");

        VisualKeyboardUIBL visualKeyboardUIBL = searchUIBL.expandVisualKeyboard();
        VisualKeyboardAssertion.baseCheckForVisualKeyboard(visualKeyboardUIBL);
        visualKeyboardUIBL.fillInSearchInputByVisualKeyboard(searchRequest);
        visualKeyboardUIBL.closeVisualKeyboard();
        Assert.assertFalse(visualKeyboardUIBL.isVisualKeyboardExpanded(), "Error - visual keyboard did not closed");

        SearchResultUIBL searchResultUIBL = searchUIBL.pressSearchButton();
        Assert.assertTrue(searchResultUIBL.getSearchUIBL().isInputFieldValueCorrect(searchRequest), "Error - result is not correct");
        Assert.assertTrue(searchResultUIBL.isResultListCorrect(searchRequest), "Error - search was unsuccessful");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify if Google search is correct then search by typing", groups = "regression")
    public void searchByTyping() {
        new NavigationPO().navigateToGoogle(UK_LOCALISATION);
        var searchUIBL = new SearchUIBL();
        String searchRequest = "ютуб вежа";
        SearchResultUIBL searchResultUIBL = searchUIBL.fillInSearchInput(searchRequest)
                .pressSearchButton();
        Assert.assertTrue(searchResultUIBL.getSearchUIBL().isInputFieldValueCorrect(searchRequest), "Error - result is not correct");
        Assert.assertTrue(searchResultUIBL.isResultListCorrect(searchRequest), "Error - search was unsuccessful");
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(dataProvider = "testData", description = "Verify if Google search is correct then search by typing with confused data", groups = "regression")
    public void searchWithConfusedData(String localisation, String firstMessagePart, String secondMessagePart) {
        new NavigationPO().navigateToGoogle(localisation);
        var searchUIBL = new SearchUIBL();
        String confusedData = "werioruwiuriwutierytyrtu";
        SearchResultUIBL searchResultUIBL = searchUIBL.fillInSearchInput(confusedData)
                .pressSearchButton();
        Assert.assertTrue(searchResultUIBL.getSearchUIBL().isInputFieldValueCorrect(confusedData), "Error - result is not correct");

        Assert.assertTrue(searchResultUIBL.isResultListEmpty(), "Error - search was success");
        String text = searchResultUIBL.getUnSuccessResultText();
        Assert.assertEquals(text, firstMessagePart + confusedData + secondMessagePart, "Error - message is incorrect");
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        return new Object[][]{
                {UK_LOCALISATION, "На запит ", " не знайдено жодного документа."},
                {EN_LOCALISATION, "Your search - ", " - did not match any documents."}};
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Verify if visual keyboard appears when launch Google with english localisation", groups = "regression")
    public void verifyVisualKeyboardForEnLocalisation() {
        new NavigationPO().navigateToGoogle(EN_LOCALISATION);
        SearchUIBL searchUIBL = new SearchUIBL();
        Assert.assertFalse(searchUIBL.isTransliterationButtonDisplayed(), "Error - transliteration button is not displayed");
    }
}
