package com.google.business;

import com.google.business.initial.SearchUIBL;
import com.google.listener.LogListener;
import com.google.pageobgect.resultpage.SearchResultPage;
import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchResultUIBL {

    private SearchUIBL searchUIBL;
    private SearchResultPage searchResultPage;

    public SearchResultUIBL() {
        searchUIBL = new SearchUIBL();
        LogListener.logger.info("Verify Search result page");
        searchResultPage = new SearchResultPage();
    }

    public SearchUIBL getSearchUIBL() {
        return searchUIBL;
    }

    @Step("Check that list of result is correct")
    public boolean isResultListCorrect(String searchValue) {
        int minCoincidenceCount = 1;
        int zeroCoincidenceCount = 0;
        Map<Boolean, List<Integer>> result = searchResultPage.getResultList()
                .stream()
                .map(webElement -> {
                    long count = Arrays.stream(searchValue.split("[\\s@&.?$+-]+")).filter(value -> webElement.getText()
                            .toLowerCase()
                            .contains(value.toLowerCase()))
                            .count();
                    if (count >= minCoincidenceCount) {
                        return (int) count;
                    } else {
                        return zeroCoincidenceCount;
                    }
                }).collect(Collectors.partitioningBy(value -> value > zeroCoincidenceCount));
        return !result.get(Boolean.TRUE).isEmpty();
    }

    @Step("Check that list of result displayed after searched with incorrect data")
    public boolean isResultListEmpty() {
        return !searchResultPage.getIncorrectSearchResult().isEmpty();
    }

    @Step("Get text from unsuccess search result")
    public String getUnSuccessResultText() {
        return searchResultPage.getIncorrectSearchResult().get(0).getText();
    }
}
