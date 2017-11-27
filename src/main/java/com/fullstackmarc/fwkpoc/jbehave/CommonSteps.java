package com.fullstackmarc.fwkpoc.jbehave;

import com.fullstackmarc.fwkpoc.exceptions.PageNotMappedException;
import com.fullstackmarc.fwkpoc.selenium.TestScope;
import com.fullstackmarc.fwkpoc.selenium.pages.PageFactory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Steps
public class CommonSteps {

    private static final Logger LOG = LoggerFactory.getLogger(CommonSteps.class);
    private static final String CURRENT_PAGE = "CURRENT_PAGE";

    private final PageFactory pageFactory;
    private final TestScope testScope;

    @Autowired
    public CommonSteps(PageFactory pageFactory, TestScope testScope) {
        this.pageFactory = pageFactory;
        this.testScope = testScope;
    }

    @Given("the user is in the $page page")
    public void givenUserInPage(String page) throws PageNotMappedException {
        LOG.info("Given the user is in the {} page.", page);
        testScope.put(CURRENT_PAGE, pageFactory.getPage(page).navigate());
    }

    @Given("the page is populated with random data")
    public void givenPagePopulatedRandom(String page) {
        LOG.info("Given the user is in the " + page + " page.");
    }

    @When("nothing happens")
    public void whenNothingHappens() {

    }

    @Then("nothing will happen")
    public void thenNothingWillHappen() {

    }
}
