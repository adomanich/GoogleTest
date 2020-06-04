package com.google.pageobgect.initialpage;

import com.google.pageobgect.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VisualKeyboard extends BasePage {

    @FindBy(xpath = ".//*[contains(@class, 'ita-container')]")
    private List<WebElement> keyboardContainer;

    @FindBy(xpath = ".//*[contains(@class, 'btns')]/*[contains(@class,'btn')]")
    private WebElement keyboardContainerCloseButton;

    public List<WebElement> getKeyboardContainer() {
        return keyboardContainer;
    }

    public WebElement getKeyboardContainerCloseButton() {
        wait.until(ExpectedConditions.visibilityOf(keyboardContainerCloseButton));
        return keyboardContainerCloseButton;
    }

    public WebElement getSymbol(char symbol) {
        return driver.findElement(By.xpath(".//*[contains(@class, 'ita-container')]//span[contains(text(),'" + symbol + "')]/.."));
    }

    public WebElement getSymbol(String symbolId) {
        return driver.findElement(By.id("K" + symbolId));
    }
}
