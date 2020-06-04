package com.google.business.initial;

import com.google.listener.LogListener;
import com.google.pageobgect.initialpage.VisualKeyboard;
import com.google.webdriver.DriverUtil;
import io.qameta.allure.Step;

public class VisualKeyboardUIBL {

    private VisualKeyboard visualKeyboard;

    public VisualKeyboardUIBL() {
        LogListener.logger.info("Verify Snapshot project pop-up page");
        visualKeyboard = new VisualKeyboard();
    }


    @Step("Fill into search input field")
    public void fillInSearchInputByVisualKeyboard(String searchValue) {
        LogListener.logger.info("Fill in search input using visual keyboard");
        final String SPACE_ID = "32";
        final String SHIFT_ID = "16";
        searchValue.chars()
                .mapToObj(ch -> (char) ch)
                .forEach(ch -> {
                    if (ch == ' ') {
                        pressOnSymbol(SPACE_ID);
                    } else if (Character.isUpperCase(ch)) {
                        pressOnSymbol(SHIFT_ID);
                        pressOnSymbol(ch);
                    } else {
                        pressOnSymbol(ch);
                    }
                });
    }

    @Step("Click on the symbol")
    private void pressOnSymbol(char symbol) {
        visualKeyboard.getSymbol(symbol).click();
    }

    @Step("Click on special symbol")
    private void pressOnSymbol(String symbolId) {
        visualKeyboard.getSymbol(symbolId).click();
    }

    @Step("Click on close button")
    public void closeVisualKeyboard() {
        LogListener.logger.info("Close Visual Keyboard");
        new DriverUtil().clickOnElementJS(visualKeyboard.getKeyboardContainerCloseButton());
    }

    @Step("Check that visual keyboard expanded")
    public boolean isVisualKeyboardExpanded() {
        return !visualKeyboard.getKeyboardContainer().get(0).getAttribute("style").contains("display: none");
    }

    @Step("Check that close button enabled")
    public boolean isCloseButtonEnabled() {
        return visualKeyboard.getKeyboardContainerCloseButton().isDisplayed();
    }
}
