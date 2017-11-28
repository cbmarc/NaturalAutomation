package com.fullstackmarc.fwkpoc.jbehave;

import com.fullstackmarc.fwkpoc.exceptions.PageNotMappedException;
import com.fullstackmarc.fwkpoc.selenium.TestScope;
import com.fullstackmarc.fwkpoc.selenium.pages.Page;
import com.fullstackmarc.fwkpoc.selenium.pages.PageFactory;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Steps
public class CommonSteps {

    private static final Logger LOG = LoggerFactory.getLogger(CommonSteps.class);
    private static final String CURRENT_PAGE = "CURRENT_PAGE";

    private final PageFactory pageFactory;
    private final TestScope testScope;

    private String currentStory;



    @Autowired
    public CommonSteps(PageFactory pageFactory, TestScope testScope) {
        this.pageFactory = pageFactory;
        this.testScope = testScope;
    }

    @Given("I am in the $page page")
    public void givenUserInPage(@Named("page") String page) throws PageNotMappedException {
        LOG.info("Given the user is in the {} page.", page);
        testScope.put(CURRENT_PAGE, pageFactory.getPage(page).navigate());
    }

    @Given("I populate the page with random data")
    public void givenPagePopulatedRandom() {
        LOG.info("Given the page is populated with random data.");
        ((Page) testScope.get(CURRENT_PAGE)).fillDefaultData();
    }

    @Given("I have written '$text' in the $field field")
    public void givenTextInFieldIsWritten(@Named("text") String text, @Named("field") String field) throws NoSuchFieldException {
        ((Page) testScope.get(CURRENT_PAGE)).writeTextInField(field, text);
    }

    @Given("I have selected the '$option' option in the $field dropdown")
    public void givenSelectedOption(@Named("option") String option, @Named("field") String field) throws NoSuchFieldException {
        ((Page) testScope.get(CURRENT_PAGE)).selectOptionInField(field, option);
    }

    @Given("I want to add this data to the page $examples")
    public void givenAddData(@Named("examples") ExamplesTable examples) throws NoSuchFieldException {
        testScope.put("examples", examples);
    }

    @When("I $action")
    public void whenIDoAnAction(@Named("action") String action) {
        LOG.info("When the user does a {}.", action);
        testScope.put(CURRENT_PAGE, ((Page) testScope.get(CURRENT_PAGE)).invokeAction(action));
    }

    @Then("there should be $collectionName")
    public void thenThereShouldBeResults(@Named("collectionName") String collectionName) throws NoSuchFieldException, IllegalAccessException {
        LOG.info("Then there should be {}.", collectionName);
        Collection collection = (Collection) ((Page) testScope.get(CURRENT_PAGE)).getFieldValue(collectionName);
        Assert.assertThat(collection, Matchers.notNullValue());
        Assert.assertThat(collection.size(), Matchers.greaterThan(0));
    }
}
