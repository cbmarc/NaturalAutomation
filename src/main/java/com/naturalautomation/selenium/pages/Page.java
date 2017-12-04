package com.naturalautomation.selenium.pages;

import com.naturalautomation.exceptions.NotInPageException;
import com.naturalautomation.selenium.element.Element;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

public abstract class Page {

    private static final Logger LOG = LoggerFactory.getLogger(Page.class);
    private static final String IMPORT_KEY = "key";
    private static final String IMPORT_VALUE = "value";
    private WebDriver driver;

    @Autowired
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void fillDefaultData() {
        Stream.of(this.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(InputData.class))
                .forEach(f -> setFieldValue(f, random(String.class)));
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
        driver.get(getURL());
        selectIFrame();
        if (!isInPage()) throw new NotInPageException(this);
        return this;
    }

    /**
     * Function to get the current PAGE_URL.
     * A page can override this function to get its url instead.
     */
    protected String getURL() {
        return driver.getCurrentUrl();
    }

    protected abstract boolean isInPage();


    /**
     * Defaults to no iframe
     */
    protected void selectIFrame() {
        driver.switchTo().defaultContent();
    }

    public Set<Exception> importKeyValuePairsIntoFields(Collection<Map<String, String>> rows) {
        final Set<Exception> nestedExceptions = new HashSet<>();
        rows.forEach(r -> {
            try {
                setFieldValue(r.get(IMPORT_KEY), r.get(IMPORT_VALUE));
            } catch (NoSuchFieldException e) {
                nestedExceptions.add(e);
            }
        });
        return nestedExceptions;
    }

    public Set<Exception> importNamedValuesIntoFields(Collection<String> headers, Collection<Map<String, String>> rows) {
        final Set<Exception> nestedExceptions = new HashSet<>();
        headers.forEach(h -> rows.forEach(r -> {
            try {
                setFieldValue(h, r.get(h));
            } catch (NoSuchFieldException e) {
                nestedExceptions.add(e);
            }
        }));
        return nestedExceptions;
    }

    public void setFieldValue(String fieldName, CharSequence value) throws NoSuchFieldException {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            setFieldValue(field, value);
        } catch (NoSuchFieldException e) {
            LOG.error("Error when trying to set field value for field: " + fieldName + ".", e);
            throw e;
        }
    }

    private void setFieldValue(Field f, CharSequence value) {
        try {
            f.setAccessible(true);
            Element element = (Element) f.get(this);
            element.click();
            element.inputValue(value);
            f.setAccessible(false);
        } catch (IllegalAccessException e) {
            LOG.warn("Error setting default random data.", e);
        }
    }

}
