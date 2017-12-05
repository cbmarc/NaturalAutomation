package com.naturalautomation.selenium.element.ui;

import com.naturalautomation.selenium.element.DefaultElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;

public class DefaultSelectElement extends DefaultElement implements SelectElement {

    public DefaultSelectElement(WebElement element, WebDriver driver) {
        super(element, driver);
    }

    public void chooseOption(String visibleText) {
        new Select(this)
                .selectByVisibleText(visibleText);
    }

    @Override
    public void inputValue(CharSequence... value) {
        this.chooseOption(Arrays.toString(value));
    }
}
