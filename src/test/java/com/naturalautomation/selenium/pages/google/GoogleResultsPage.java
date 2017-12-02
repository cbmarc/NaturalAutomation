package com.naturalautomation.selenium.pages.google;


import com.naturalautomation.selenium.element.base.Element;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageObject;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject
public class GoogleResultsPage extends Page implements Named {

    private static final String PAGE_NAME = "Google results";

    @FindBy(className = "g")
    private List<Element> results;

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    @Override
    protected boolean isInPage() {
        return true;
    }
}
