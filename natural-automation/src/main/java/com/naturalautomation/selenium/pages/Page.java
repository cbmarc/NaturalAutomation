package com.naturalautomation.selenium.pages;

import com.naturalautomation.annotations.InputData;
import com.naturalautomation.exceptions.NaturalAutomationException;
import com.naturalautomation.exceptions.NotInPageException;
import com.naturalautomation.jbehave.WebDriverWrapper;
import com.naturalautomation.selenium.element.Element;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public abstract class Page {

    private static final Logger LOG = LoggerFactory.getLogger(Page.class);
    private static final String IMPORT_KEY = "key";
    private static final String IMPORT_VALUE = "value";
    private static final long WAIT_TIMEOUT = 10L;

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    protected WebDriver getDriver() {
        return webDriverWrapper.getWebDriver();
    }

    public String getTitle() {
        return webDriverWrapper.getWebDriver().getTitle();
    }

    public void fillDefaultData() {
        Stream.of(this.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(InputData.class))
                .forEach(f -> setFieldValue(f, EnhancedRandom.random(String.class)));
    }

    public Object getFieldValue(String fieldName) {
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
            throw new NaturalAutomationException(e);
        }
    }

    public Page invokeAction(String action, Object... params) {
        Stream<Method> methods = Arrays.stream(this.getClass().getDeclaredMethods());
        Method method = methods
                .filter(a -> a.getName().equals(action))
                .filter(a -> a.getParameterCount() == params.length)
                .findFirst()
                .orElseThrow(() -> new NaturalAutomationException("Action not found:" + action));
        try {
            return (Page) method.invoke(this, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new NaturalAutomationException("Error while executing action " + action + " with params size " + params.length, e);
        }
    }

    public Page navigate() {
        webDriverWrapper.getWebDriver().get(getURL());
        selectIFrame();
        if (!isInPage()) throw new NotInPageException(this);
        return this;
    }

    /**
     * Function to get the current PAGE_URL.
     * A page can override this function to get its url instead.
     */
    protected String getURL() {
        return webDriverWrapper.getWebDriver().getCurrentUrl();
    }

    protected abstract boolean isInPage();


    /**
     * Defaults to no iframe
     */
    protected void selectIFrame() {
        webDriverWrapper.getWebDriver().switchTo().defaultContent();
    }

    public void importKeyValuePairsIntoFields(Collection<Map<String, String>> rows) {
        rows.forEach(r -> setFieldValue(r.get(IMPORT_KEY), r.get(IMPORT_VALUE)));
    }

    public void importNamedValuesIntoFields(Collection<String> headers, Collection<Map<String, String>> rows) {
        headers.forEach(h -> rows.forEach(r -> setFieldValue(h, r.get(h))));
    }

    public void clickOnElement(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            new WebDriverWait(getDriver(), WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf((Element)field.get(this)));
            Element element = (Element) field.get(this);
            element.click();
            field.setAccessible(false);
        } catch (Exception e) {
            LOG.error("Error when trying to set field value for field: " + fieldName + ".", e);
            throw new NaturalAutomationException(e);
        }
    }

    public void waitUntilVisible(Element element) {
        new WebDriverWait(getDriver(), WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void setFieldValue(String fieldName, CharSequence value)  {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            setFieldValue(field, value);
        } catch (NoSuchFieldException e) {
            LOG.error("Error when trying to set field value for field: " + fieldName + ".", e);
            throw new NaturalAutomationException(e);
        }
    }

    private void setFieldValue(Field f, CharSequence value) {
        try {
            f.setAccessible(true);
            new WebDriverWait(webDriverWrapper.getWebDriver(),10000L).until(ExpectedConditions.visibilityOf((Element)f.get(this)));
            Element element = (Element) f.get(this);
            element.click();
            element.inputValue(value);
            f.setAccessible(false);
        } catch (IllegalAccessException e) {
            LOG.error("Error setting data to field " + f.getName(), e);
            throw new NaturalAutomationException("Error setting data to field " + f.getName(), e);
        }
    }

}
