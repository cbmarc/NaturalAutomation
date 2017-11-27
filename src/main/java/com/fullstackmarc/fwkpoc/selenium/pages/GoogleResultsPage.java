package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@PageObject
public class GoogleResultsPage extends Page implements Named {

    private static final String PAGE_NAME = "Google results";

    @FindBy(className = "g")
    private WebElement[] results;

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    public int getResults() {
        return results.length;
    }

    @Override
    boolean isInPage() {
        return false;
    }
}
