package com.fullstackmarc.fwkpoc.jbehave;

import com.fullstackmarc.fwkpoc.config.AcceptanceTestsConfiguration;
import com.fullstackmarc.fwkpoc.selenium.SeleniumTestExecutionListener;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ContextConfiguration(classes = AcceptanceTestsConfiguration.class)
@ImportResource({"classpath:/application.properties", "classpath:/tests.properties"})
@ActiveProfiles("tests")
@DirtiesContext
@Target(ElementType.TYPE)
@TestExecutionListeners({ SeleniumTestExecutionListener.class, DependencyInjectionTestExecutionListener.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptanceTest {
}