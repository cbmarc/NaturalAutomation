package com.fullstackmarc.fwkpoc.jbehave;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Steps
public class CommonSteps {

    private static final Logger LOG = LoggerFactory.getLogger(CommonSteps.class);

    @Given("the page is populated with random data")
    public void givenPagePopulatedRandom(String page) {
        LOG.info("Given the user is in the " + page + " page.");
    }

    @Given("the user is in the $page page")
    public void givenUserInPage(String page) {
        LOG.info("Given the user is in the {} page.", page);
    }

    @When("nothing happens")
    public void whenNothingHappens() {

    }

    @Then("nothing will happen")
    public void thenNothingWillHappen() {

    }
}
