package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public class GooglePage extends Page implements Named {

    private static final String PAGE_NAME = "Google";
    private static final String GOOGLE_ES = "http://www.google.es";
    private static final String PAGE_URL = GOOGLE_ES;

    private final GoogleResultsPage googleResultsPage;

    @InputData
    @FindBy(id = "lst-ib")
    private WebElement searchBox;

    @FindBy(name = "btnK")
    private WebElement searchButton;

    @Autowired
    public GooglePage(GoogleResultsPage googleResultsPage) {
        this.googleResultsPage = googleResultsPage;
    }

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    public GoogleResultsPage search() {
        click(searchButton);
        return googleResultsPage;
    }

    public GoogleResultsPage search(String text) {
        inputText(searchBox, text);
        return search();
    }

    @Override
    String getURL() {
        return PAGE_URL;
    }

    @Override
    boolean isInPage() {
        return false;
    }

}
