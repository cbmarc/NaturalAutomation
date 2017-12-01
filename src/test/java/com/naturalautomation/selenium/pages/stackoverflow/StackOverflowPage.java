package com.naturalautomation.selenium.pages.stackoverflow;


import com.naturalautomation.selenium.components.html.HtmlComponent;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public class StackOverflowPage extends Page implements Named {

    private static final String PAGE_NAME = "StackOverflow";
    private static final String PAGE_URL = "https://stackoverflow.com/";

    private final StackOverflowJobsPage jobsPage;

    @FindBy(id = "h-top-questions")
    private HtmlComponent questionsHeader;

    @FindBy(id = "nav-jobs")
    private HtmlComponent jobsMenuLink;

    @Autowired
    public StackOverflowPage(StackOverflowJobsPage jobsPage) {
        this.jobsPage = jobsPage;
    }

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
        return questionsHeader.isDisplayed();
    }

    public StackOverflowJobsPage navigateToJobs() {
        jobsMenuLink.click();
        if (!isInPage()) {
            throw new IllegalStateException("I should be in jobs page but I'm not.");
        }
        return jobsPage;
    }

}
