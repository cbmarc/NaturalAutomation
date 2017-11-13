package com.fullstackmarc.fwkpoc.selenium;

import org.openqa.selenium.WebDriver;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class SeleniumTestExecutionListener extends AbstractTestExecutionListener {

    /***
     * Clean the scope before running a test
     * @param testContext
     * @throws Exception
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        testContext.getApplicationContext().getBean(TestScope.class).reset();
    }

    /***
     * Stop webdriver instance after test is finished
     * @param testContext
     * @throws Exception
     */
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        testContext.getApplicationContext().getBean(WebDriver.class).quit();
    }

}
