package com.naturalautomation.selenium.components.html;

import com.naturalautomation.selenium.element.DefaultElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectInput extends DefaultElement {

    public SelectInput(WebElement element, WebDriver driver) {
        super(element, driver);
    }

    public void chooseOption(String visibleText) {
        new Select(this)
            .selectByVisibleText(visibleText);
    }
}
