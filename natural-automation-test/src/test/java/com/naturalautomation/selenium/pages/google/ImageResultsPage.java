package com.naturalautomation.selenium.pages.google;

import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject
public class ImageResultsPage extends Page implements Named {

    private static final String IMAGE_RESULTS = "image results";

    @FindBy(id = "taw")
    Element sizeElement;

    @FindBy(xpath = "//div[@id='rg_s']/div")
    List<Element> results;

    @Override
    public String getName() {
        return IMAGE_RESULTS;
    }

    @Override
    protected boolean isInPage() {
        return sizeElement.isDisplayed();
    }
}
