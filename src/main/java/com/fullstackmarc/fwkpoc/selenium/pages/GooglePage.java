package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@PageObject
public class GooglePage extends Page implements Named {

    private static final String PAGE_NAME = "Google";

    private final GoogleResultsPage googleResultsPage;

    @Value("${google.page.search.input.id}")
    private String searchBoxId;

    @Value("${google.page.search.button.name}")
    private String searchButtonName;

    @Autowired
    public GooglePage(GoogleResultsPage googleResultsPage) {
        this.googleResultsPage = googleResultsPage;
    }

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    public GooglePage typeSearch(String text) {
        getDriver().findElement(By.id(searchBoxId)).sendKeys(text);
        return this;
    }

    public GoogleResultsPage clickSearchButton() {
        getDriver().findElement(By.name(searchButtonName)).click();
        return googleResultsPage;
    }
}
