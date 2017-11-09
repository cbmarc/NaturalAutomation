package com.fullstackmarc.selenium;

import com.fullstackmarc.selenium.jbehave.AbstractSpringJBehaveStories;
import com.fullstackmarc.selenium.jbehave.AcceptanceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AcceptanceTest
public class JBehaveStoriesTest extends AbstractSpringJBehaveStories {

    @Test
    public void testIt() throws Exception {
        System.out.println("BLAH");
    }
}