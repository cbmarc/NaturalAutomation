package com.naturalautomation.selenium.pages.google;


import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.selenium.pages.Page;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject(name = "Google results")
public class ResultsPage extends Page {


    @FindBy(className = "g")
    private List<Element> results;

    @FindBy(xpath = "//div[@id='hdtb-msb-vis']/*/a[contains(text(), 'Im')]")
    private Element imagesLink;


    @Override
    protected boolean isInPage() {
        return true;
    }

    public ImageResultsPage clickOnImagesLink() {
        imagesLink.click();
        return (ImageResultsPage) pageFactory.getPage("Google results");
    }

}
