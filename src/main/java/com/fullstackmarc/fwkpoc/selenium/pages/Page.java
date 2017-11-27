package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

public abstract class Page {

    private static final Logger LOG = LoggerFactory.getLogger(Page.class);
    private WebDriver driver;

    WebDriver getDriver() {
        return driver;
    }

    @Autowired
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Function to get the current PAGE_URL.
     * A page can override this function to get its url instead.
     */
    protected String getURL() {
        return driver.getCurrentUrl();
    }

    public void fillDefaultData() {
        List<Field> fields = Arrays.asList(this.getClass().getDeclaredFields());
        fields.stream()
                .filter(f -> f.isAnnotationPresent(InputData.class))
                .forEach(f -> this.setFieldValue(f, random(String.class), this::inputText));

    }

    public Object getFieldValue(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            boolean accesible = field.isAccessible();
            field.setAccessible(true);
            Object obj = field.get(this);
            // Restore previous state
            field.setAccessible(accesible);
            return obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOG.error("Error when trying to get field value for field: " + fieldName + ".", e);
            throw e;
        }
    }

    public void writeTextInField(String fieldName, String value) throws NoSuchFieldException {
        this.setFieldValue(fieldName, value, this::inputText);
    }

    public void selectOptionInField(String fieldName, String value) throws NoSuchFieldException {
        this.setFieldValue(fieldName, value, this::chooseOptionFromSelect);
    }

    private void setFieldValue(String fieldName, String value, BiConsumer<WebElement, String> consumer) throws NoSuchFieldException {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            setFieldValue(field, value, consumer);
        } catch (NoSuchFieldException e) {
            LOG.error("Error when trying to set field value for field: " + fieldName + ".", e);
            throw e;
        }
    }

    public Page invokeAction(String action, Object ... params) {
        List<Method> methods = Arrays.asList(this.getClass().getDeclaredMethods());
        Optional<Method> optMethod = methods.stream()
                .filter(a -> a.getName().equals(action))
                .filter(a -> a.getParameterCount() == params.length)
                .findFirst();
        if (optMethod.isPresent()) {
            try {
                return (Page) optMethod.get().invoke(this, params);
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOG.error("Error while executing action " + action + " with params size " + params.length, e);
            }
        } else {
            LOG.error("Action {} does not exist.", action);
        }
        return null;
    }

    protected void inputText(WebElement element, String text) {
        Actions actions = new Actions(driver);
        actions
                .moveToElement(element)
                .click(element)
                .sendKeys(element, text)
                .build().perform();
        LOG.info("Sending text to element: {}", text);
    }

    protected void chooseOptionFromSelect(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    protected void pressEnter(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .sendKeys(element, Keys.ENTER)
                .build().perform();
    }

    protected void click(WebElement element) {
        Actions actions = new Actions(driver);
        actions.click(element).build().perform();
    }

    protected abstract boolean isInPage();

    public Page navigate() {
        driver.get(getURL());
        return this;
    }

    private void setFieldValue(Field f, String value, BiConsumer<WebElement, String> consumer) {
        try {
            f.setAccessible(true);
            WebElement element = (WebElement) f.get(this);
            click(element);
            consumer.accept(element, value);
            f.setAccessible(false);
        } catch (IllegalAccessException e) {
            LOG.warn("Error setting default random data.", e);
        }
    }

}
