package com.naturalautomation.selenium.pages.stackoverflow;


import com.naturalautomation.selenium.components.html.HtmlComponent;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject
public class StackOverflowJobsPage extends Page implements Named {

    private static final String PAGE_NAME = "StackOverflowJobs";
    private static final String PAGE_URL = "https://stackoverflow.com/jobs";

    @FindBy(id = "nav-jobs")
    private HtmlComponent jobsMenuLink;

    @FindBy(id = "q")
    private HtmlComponent searchBox;

    @FindBy(id = "l")
    private HtmlComponent locationBox;

    @FindBy(id = "sorting-options")
    private HtmlComponent sortedBy;

    @FindBy(className = "-job")
    private List<HtmlComponent> results;

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    @Override
    protected String getURL() {
        return PAGE_URL;
    }

    @Override
    protected boolean isInPage() {
        return jobsMenuLink.findElement(By.xpath("..")).getAttribute("class").contains("_current");
    }

    public StackOverflowJobsPage search() {
        searchBox.pressEnter();
        return this;
    }

}
