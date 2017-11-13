package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractPage {

    protected WebDriver driver;

    @Autowired
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
