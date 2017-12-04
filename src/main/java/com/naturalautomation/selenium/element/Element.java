package com.naturalautomation.selenium.element;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

/**
 * WebElement wrapper to add and enhance functionality.
 */
public interface Element extends WebElement, WrapsElement, Locatable {
    void pressEnter();

    void inputValue(CharSequence... value);
}
