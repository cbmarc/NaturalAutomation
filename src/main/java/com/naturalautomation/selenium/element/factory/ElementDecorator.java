package com.naturalautomation.selenium.element.factory;

import com.naturalautomation.exceptions.ImplementationNotFoundException;
import com.naturalautomation.selenium.element.DefaultElement;
import com.naturalautomation.selenium.element.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.List;

/**
 * WrappedElementDecorator recognizes a few things that DefaultFieldDecorator does not.
 * It is designed to support and return concrete implementations of wrappers for a variety of common html elements.
 */
public class ElementDecorator implements FieldDecorator {

    private final static Logger LOG = LoggerFactory.getLogger(ElementDecorator.class);
    /**
     * factory to use when generating ElementLocator.
     */
    private ElementLocatorFactory factory;
    private WebDriver driver;

    /**
     * Constructor for an ElementLocatorFactory. This class is designed to replace DefaultFieldDecorator.
     *
     * @param factory for locating elements.
     */
    public ElementDecorator(ElementLocatorFactory factory, WebDriver driver) {
        this.factory = factory;
        this.driver = driver;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
            return null;
        }

        if (field.getDeclaringClass() == DefaultElement.class) {
            return null;
        }

        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        Class<?> fieldType = field.getType();
        if (WebElement.class.equals(fieldType)) {
            fieldType = Element.class;
        }

        if (WebElement.class.isAssignableFrom(fieldType)) {
            return proxyForLocator(loader, fieldType, locator);
        } else if (List.class.isAssignableFrom(fieldType)) {
            Class<?> erasureClass = getErasureClass(field);
            return proxyForListLocator(loader, erasureClass, locator);
        } else {
            return null;
        }
    }

    private Class<?> getErasureClass(Field field) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        Class<?> erasureClass = getErasureClass(field);
        return erasureClass != null
                && WebElement.class.isAssignableFrom(erasureClass)
                && (field.getAnnotation(FindBy.class) != null
                || field.getAnnotation(FindBys.class) != null
                || field.getAnnotation(FindAll.class) != null);

    }

    private <T> T proxyForLocator(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {
        try {
            InvocationHandler handler = new ElementHandler(interfaceType, locator, driver);

            T proxy;
            proxy = interfaceType.cast(Proxy.newProxyInstance(
                    loader, new Class[]{interfaceType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
            return proxy;
        } catch (ImplementationNotFoundException e) {
            LOG.error(e.getMessage(), e);
            // Have to return null as call, as caller class is legacy and not under our domain
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> proxyForListLocator(ClassLoader loader, Class<T> interfaceType, ElementLocator locator) {
        try {
            InvocationHandler handler = new ElementListHandler(interfaceType, locator, driver);

            List<T> proxy;
            proxy = (List<T>) Proxy.newProxyInstance(
                    loader, new Class[]{List.class}, handler);
            return proxy;
        } catch (ImplementationNotFoundException e) {
            LOG.error(e.getMessage(), e);
            // Have to return null as call, as caller class is legacy and not under our domain
            return null;
        }
    }
}
