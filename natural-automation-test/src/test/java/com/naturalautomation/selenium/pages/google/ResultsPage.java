package com.naturalautomation.selenium.pages.google;


import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageObject
public class ResultsPage extends Page implements Named {

    private static final String PAGE_NAME = "Google results";

    @Autowired
    private ImageResultsPage imageResultsPage;

    @FindBy(className = "g")
    private List<Element> results;

    @FindBy(xpath = "//div[@id='hdtb-msb-vis']/*/a[contains(text(), 'Im')]")
    private Element imagesLink;

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    @Override
    protected boolean isInPage() {
        return true;
    }

    public ImageResultsPage clickOnImagesLink() {
        imagesLink.click();
        return imageResultsPage;
    }

}
