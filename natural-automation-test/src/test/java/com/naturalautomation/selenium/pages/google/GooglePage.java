package com.naturalautomation.selenium.pages.google;

import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.selenium.pages.InputData;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageObject;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public class GooglePage extends Page implements Named {

    private static final String PAGE_NAME = "Google";
    private static final String PAGE_URL = "http://www.google.es";

    private final GoogleResultsPage googleResultsPage;

    @InputData
    @FindBy(id = "lst-ib")
    private Element searchBox;

    @FindBy(name = "btnK")
    private Element searchButton;

    @Autowired
    public GooglePage(GoogleResultsPage googleResultsPage) {
        this.googleResultsPage = googleResultsPage;
    }

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    public GoogleResultsPage search() {
        searchBox.pressEnter();
        return googleResultsPage;
    }

    public GoogleResultsPage search(String text) {
        searchBox.sendKeys(text);
        return search();
    }

    @Override
    protected String getURL() {
        return PAGE_URL;
    }

    @Override
    protected boolean isInPage() {
        return true;
    }

}
