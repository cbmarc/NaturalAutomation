package com.fullstackmarc.fwkpoc.selenium.pages.google;

import com.fullstackmarc.fwkpoc.selenium.pages.InputData;
import com.fullstackmarc.fwkpoc.selenium.pages.Named;
import com.fullstackmarc.fwkpoc.selenium.pages.Page;
import com.fullstackmarc.fwkpoc.selenium.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public class GooglePage extends Page implements Named {

    private static final String PAGE_NAME = "Google";
    private static final String PAGE_URL = "http://www.google.es";

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
        pressEnter(searchBox);
        return googleResultsPage;
    }

    public GoogleResultsPage search(String text) {
        inputText(searchBox, text);
        return search();
    }

    @Override
    protected String getURL() {
        return PAGE_URL;
    }

    @Override
    protected boolean isInPage() {
        return false;
    }

}
