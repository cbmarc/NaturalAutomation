package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.WebDriver;

public class AbstractPage {

    protected final WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

}
