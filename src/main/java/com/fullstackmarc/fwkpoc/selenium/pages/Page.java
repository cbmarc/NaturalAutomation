package com.fullstackmarc.fwkpoc.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
     * Function to get the current URL.
     * A page can override this function to get its url instead.
     */
    protected String getURL() {
        return driver.getCurrentUrl();
    }

    public void fillDefaultData() {
        List<Field> fields = Arrays.asList(this.getClass().getDeclaredFields());
        fields.stream()
                .filter(f -> f.isAnnotationPresent(InputData.class))
                .forEach(this::acceptFieldValue);

    }

    public Object getFieldValue(String field) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(field, this.getClass());
            return descriptor.getReadMethod().invoke(this);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOG.error("Error when trying to get field value for field: " + field + ".", e);
        }
        return null;
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

    private void acceptFieldValue(Field f) {
        try {
            f.setAccessible(true);
            WebElement element = (WebElement) f.get(this);
            inputText(element, random(String.class));
            f.setAccessible(false);
        } catch (IllegalAccessException e) {
            LOG.warn("Error setting default random data.", e);
        }
    }
}
