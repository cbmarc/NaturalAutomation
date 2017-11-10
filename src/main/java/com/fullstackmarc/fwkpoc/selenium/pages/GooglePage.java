package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

public class GooglePage extends AbstractPage implements Named  {

    public static final String PAGE_NAME = "Google page";

    @Value("${google.page.search.input.id}")
    private String searchBoxId;

    @Value("${google.page.search.button.name}")
    private String searchButtonName;

    public GooglePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    public GooglePage typeSearch(String text) {
        driver.findElement(By.id(searchBoxId)).sendKeys(text);
        return this;
    }

    public GoogleResultsPage clickSearchButton() {
        driver.findElement(By.name(searchButtonName)).click();
        /**
         * TODO: Redo
         */
        return new GoogleResultsPage(driver);
    }
}
