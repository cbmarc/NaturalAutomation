package com.naturalautomation.selenium.components.html;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;

import java.util.List;

public class HtmlComponent implements WebElement {

    private static final String ATTRIBUTE_NAME = "name";

    private WebElement wrappedElement;
    private WebDriver driver;

    public HtmlComponent() {
    }

    public HtmlComponent(WebDriver driver, WebElement wrappedElement) {
        this.driver = driver;
        this.wrappedElement = wrappedElement;
    }

    public void type(String text) {
        new Actions(driver)
                .sendKeys(text)
                .build()
                .perform();
    }

    @Override
    public void click() {
        new Actions(driver)
                .click()
                .build()
                .perform();
    }

    @Override
    public void submit() {
        this.wrappedElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        new Actions(driver)
                .sendKeys(keysToSend)
                .build()
                .perform();
    }

    @Override
    public void clear() {
        this.wrappedElement.clear();
    }

    @Override
    public String getTagName() {
        return this.wrappedElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return this.wrappedElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return this.wrappedElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return this.wrappedElement.isEnabled();
    }

    @Override
    public String getText() {
        return this.wrappedElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return this.wrappedElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return this.wrappedElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return this.wrappedElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return this.wrappedElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return this.wrappedElement.getSize();
    }

    @Override
    public String getCssValue(String propertyName) {
        return this.wrappedElement.getCssValue(propertyName);
    }

    public void pressEnter() {
        new Actions(driver)
                .sendKeys(wrappedElement, Keys.ENTER)
                .build()
                .perform();
    }

    public String getName() {
        return getAttribute(ATTRIBUTE_NAME);
    }

    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return this.wrappedElement.getScreenshotAs(target);
    }


    public Coordinates getCoordinates() {
        return null;
    }
}
