package com.fullstackmarc.selenium.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Steps
public class SharedSteps {

    private static final Logger LOG = LoggerFactory.getLogger(SharedSteps.class);

    @Given("the user is in the $page page")
    public void givenUserInPage(String page) {
        LOG.info("Given the user is in the " + page + " page");
    }

    @When("nothing happens")
    public void whenNothingHappens() {

    }

    @Then("nothing will happen")
    public void thenNothingWillHappen() {

    }
}
