package com.naturalautomation.selenium.element;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import java.util.List;

public class DefaultElement implements Element {

    private final WebElement element;
    private final WebDriver driver;

    public DefaultElement(final WebElement element, final WebDriver driver) {
        this.element = element;
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void click() {
        new Actions(driver)
                .click(element)
                .build()
                .perform();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        new Actions(driver)
                .sendKeys(element, keysToSend)
                .build()
                .perform();
    }

    public void pressEnter() {
        new Actions(driver)
                .sendKeys(element, Keys.ENTER)
                .build()
                .perform();
    }

    @Override
    public Point getLocation() {
        return element.getLocation();
    }

    @Override
    public void submit() {
        element.submit();
    }

    @Override
    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    @Override
    public String getCssValue(String propertyName) {
        return element.getCssValue(propertyName);
    }

    @Override
    public Dimension getSize() {
        return element.getSize();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return element.findElements(by);
    }

    @Override
    public String getText() {
        return element.getText();
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public boolean isSelected() {
        return element.isSelected();
    }

    @Override
    public WebElement findElement(By by) {
        return element.findElement(by);
    }

    @Override
    public boolean isEnabled() {
        return element.isEnabled();
    }

    @Override
    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    @Override
    public void clear() {
        element.clear();
    }

    @Override
    public WebElement getWrappedElement() {
        return element;
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) element).getCoordinates();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return element.getScreenshotAs(target);
    }
}
