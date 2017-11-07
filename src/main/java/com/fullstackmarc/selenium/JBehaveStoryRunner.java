package com.fullstackmarc.selenium;

import com.fullstackmarc.selenium.jbehave.AcceptanceTest;
import com.fullstackmarc.selenium.config.AcceptanceTestsConfiguration;
import com.fullstackmarc.selenium.jbehave.AbstractSpringJBehaveStories;
import com.fullstackmarc.selenium.jbehave.SharedSteps;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AcceptanceTest
public class JBehaveStoryRunner extends AbstractSpringJBehaveStories {
}