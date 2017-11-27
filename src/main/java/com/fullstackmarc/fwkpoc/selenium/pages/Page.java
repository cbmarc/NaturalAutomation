package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Page {

    private WebDriver driver;

    WebDriver getDriver() {
        return driver;
    }

    @Autowired
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Function to get the current URL.
     * A page can override this function to get its url instead.
     */
    String getURL() {
        return driver.getCurrentUrl();
    }

    public Page navigate() {
        driver.get(getURL());
        return this;
    }
}
