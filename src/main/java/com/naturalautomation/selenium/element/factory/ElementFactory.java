package com.naturalautomation.selenium.element.factory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Element factory for wrapped elements. Similar to {@link PageFactory}
 */
public class ElementFactory {

    /**
     * See {@link PageFactory#initElements(WebDriver driver, Class)}
     */
    public static <T> T initElements(WebDriver driver, Class<T> pageClassToProxy) {
        T page = instantiatePage(driver, pageClassToProxy);
        return initElements(driver, page);
    }

    /**
     * As
     * {@link ElementFactory#initElements(WebDriver, Class)}
     * but will only replace the fields of an already instantiated Page Object.
     *
     * @param searchContext A search context that will be used to look up the elements
     * @param page          The object with WebElement and List<WebElement> fields that should be proxied.
     * @return the initialized page-object.
     */
    public static <T> T initElements(SearchContext searchContext, T page) {
        initElements(new ElementDecorator(new DefaultElementLocatorFactory(searchContext), (WebDriver) searchContext), page);
        return page;
    }

    /**
     * see {@link PageFactory#initElements(ElementLocatorFactory, Object)}
     */
    public static void initElements(FieldDecorator decorator, Object page) {
        PageFactory.initElements(decorator, page);
    }

    /**
     * Copy of {@link PageFactory#instantiatePage(WebDriver, Class)}
     */
    private static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
                return constructor.newInstance(driver);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
