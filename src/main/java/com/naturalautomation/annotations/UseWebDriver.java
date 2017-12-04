package com.naturalautomation.annotations;


import com.naturalautomation.selenium.WebDrivers;

import java.lang.annotation.*;

import static com.naturalautomation.selenium.WebDrivers.CHROME;


@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface UseWebDriver {

     WebDrivers driver() default CHROME;
}
