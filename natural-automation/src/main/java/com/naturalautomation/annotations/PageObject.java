package com.naturalautomation.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Target(TYPE)
@Retention(RUNTIME)
@Component
@Scope("prototype")
public @interface PageObject {
    String name() default "";
}
