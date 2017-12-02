package com.naturalautomation.selenium.components.html;

import com.naturalautomation.selenium.element.base.ElementImpl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectInput extends ElementImpl {

    public SelectInput(WebElement element, WebDriver driver) {
        super(element, driver);
    }

    public void chooseOption(String visibleText) {
        new Select(this)
            .selectByVisibleText(visibleText);
    }
}
