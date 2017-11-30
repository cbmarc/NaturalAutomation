package com.fullstackmarc.fwkpoc.selenium.pages.google;

import com.fullstackmarc.fwkpoc.selenium.pages.Named;
import com.fullstackmarc.fwkpoc.selenium.pages.Page;
import com.fullstackmarc.fwkpoc.selenium.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject
public class StackOverflowJobsPage extends Page implements Named {

    private static final String PAGE_NAME = "StackOverflowJobs";
    private static final String PAGE_URL = "https://stackoverflow.com/jobs";

    @FindBy(id = "nav-jobs")
    private WebElement jobsMenuLink;

    @FindBy(id = "q")
    private WebElement searchBox;

    @FindBy(id = "l")
    private WebElement locationBox;

    @FindBy(id = "sorting-options")
    private WebElement sortedBy;

    @FindBy(className = "-job")
    private List<WebElement> results;

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
        pressEnter(searchBox);
        return this;
    }

}
