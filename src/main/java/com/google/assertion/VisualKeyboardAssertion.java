package com.google.assertion;

import com.google.business.initial.VisualKeyboardUIBL;
import org.testng.asserts.SoftAssert;

public class VisualKeyboardAssertion {

    private VisualKeyboardAssertion() {
    }

    public static void baseCheckForVisualKeyboard(VisualKeyboardUIBL visualKeyboardUIBL) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(visualKeyboardUIBL.isCloseButtonEnabled(), "Error - close button disabled");
        softAssert.assertTrue(visualKeyboardUIBL.isVisualKeyboardExpanded(), "Error - visual keyboard is absent");
        softAssert.assertAll();
    }
}
