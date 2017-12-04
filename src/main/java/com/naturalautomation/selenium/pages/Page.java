package com.naturalautomation.selenium.pages;

import com.naturalautomation.exceptions.NotInPageException;
import com.naturalautomation.selenium.components.html.SelectInput;
import com.naturalautomation.selenium.element.Element;
import com.naturalautomation.jbehave.WebDriverWrapper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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
import java.util.stream.Stream;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

public abstract class Page {

    private static final Logger LOG = LoggerFactory.getLogger(Page.class);
    @Autowired
    private WebDriverWrapper webDriverWrapper;

    public WebDriver getDriver() {
        return webDriverWrapper.getWebDriver();
    }

    public String getTitle() {
        return webDriverWrapper.getWebDriver().getTitle();
    }

    public void fillDefaultData() {
        Stream.of(this.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(InputData.class))
                .forEach(f -> setFieldValue(f, random(String.class), Element::sendKeys));
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

    public void writeTextInField(String fieldName, CharSequence value) throws NoSuchFieldException {
        this.setFieldValue(fieldName, value, Element::sendKeys);
    }

    public void selectOptionInField(String fieldName, String value) throws NoSuchFieldException {
        this.setFieldValue(fieldName, value, (htmlComponent, s) -> ((SelectInput) htmlComponent).chooseOption(s.toString()));
    }

    public Page invokeAction(String action, Object... params) {
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

    public Page navigate() throws NotInPageException {
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

    private void setFieldValue(String fieldName, CharSequence value, BiConsumer<Element, CharSequence> consumer) throws NoSuchFieldException {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            setFieldValue(field, value, consumer);
        } catch (NoSuchFieldException e) {
            LOG.error("Error when trying to set field value for field: " + fieldName + ".", e);
            throw e;
        }
    }

    private void setFieldValue(Field f, CharSequence value, BiConsumer<Element, CharSequence> consumer) {
        try {
            f.setAccessible(true);
            Element element = (Element) f.get(this);
            element.click();
            consumer.accept(element, value);
            f.setAccessible(false);
        } catch (IllegalAccessException e) {
            LOG.warn("Error setting default random data.", e);
        }
    }

}
