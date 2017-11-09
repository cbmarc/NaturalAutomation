package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class GoogleResultsPage extends AbstractPage implements Named {

    public static final String PAGE_NAME = "Google results page";

    @Value("${google.results.page.list.id}")
    private String resultsListId;

    @Value("${google.results.page.result.class}")
    private String resultClass;


    public GoogleResultsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    public int getResultCount() {
        List<WebElement> results = driver.findElement(By.id(resultsListId)).findElements(By.className(resultClass));
        return results.size();
    }

}
