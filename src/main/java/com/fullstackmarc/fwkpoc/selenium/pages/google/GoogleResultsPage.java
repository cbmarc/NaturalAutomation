package com.fullstackmarc.fwkpoc.selenium.pages.google;

import com.fullstackmarc.fwkpoc.selenium.pages.Named;
import com.fullstackmarc.fwkpoc.selenium.pages.Page;
import com.fullstackmarc.fwkpoc.selenium.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject
public class GoogleResultsPage extends Page implements Named {

    private static final String PAGE_NAME = "Google results";

    @FindBy(className = "g")
    private List<WebElement> results;

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    @Override
    protected boolean isInPage() {
        return false;
    }
}
