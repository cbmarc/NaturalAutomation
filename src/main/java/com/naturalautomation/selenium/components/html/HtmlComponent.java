package com.naturalautomation.selenium.components.html;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;

public class HtmlComponent extends RemoteWebElement implements HtmlElement {

    private static final String ATTRIBUTE_NAME = "name";

    public void type(String text) {
        new Actions(getWrappedDriver())
                .sendKeys(text)
                .build()
                .perform();
    }

    @Override
    public void click() {
        new Actions(getWrappedDriver())
                .click()
                .build()
                .perform();
    }

    public void pressEnter() {
        new Actions(getWrappedDriver())
                .sendKeys(this, Keys.ENTER)
                .build()
                .perform();
    }

    public String getName() {
        return getAttribute(ATTRIBUTE_NAME);
    }

    @Override
    public WebElement getWrappedElement() {
        return this;
    }
}
