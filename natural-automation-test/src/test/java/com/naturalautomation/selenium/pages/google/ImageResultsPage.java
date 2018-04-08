package com.naturalautomation.selenium.pages.google;

import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.selenium.pages.Page;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject(name = "image results")
public class ImageResultsPage extends Page {


    @FindBy(id = "taw")
    Element sizeElement;

    @FindBy(xpath = "//div[@id='rg_s']/div")
    List<Element> results;


    @Override
    protected boolean isInPage() {
        return sizeElement.isDisplayed();
    }
}
