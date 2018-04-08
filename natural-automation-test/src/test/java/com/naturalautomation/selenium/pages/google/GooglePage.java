package com.naturalautomation.selenium.pages.google;

import com.naturalautomation.annotations.InputData;
import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.selenium.pages.Page;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject(name = "Google")
public class GooglePage extends Page {

    private static final String PAGE_NAME = "Google";
    private static final String PAGE_URL = "http://www.google.es";

    private final ResultsPage googleResultsPage;

    @InputData
    @FindBy(id = "lst-ib")
    public Element searchBox;

    @FindBy(name = "btnK")
    public Element searchButton;

    @Autowired
    public GooglePage(ResultsPage googleResultsPage) {
        this.googleResultsPage = googleResultsPage;
    }

    public ResultsPage search() {
        searchBox.pressEnter();
        return (ResultsPage) pageFactory.getPage("Google results");
    }

    public ResultsPage search(String text) {
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
