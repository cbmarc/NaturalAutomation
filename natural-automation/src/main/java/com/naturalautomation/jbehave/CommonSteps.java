package com.naturalautomation.jbehave;

import java.util.Collection;

import org.eclipse.jetty.util.annotation.Name;
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

import com.naturalautomation.annotations.Steps;
import com.naturalautomation.selenium.TestScope;
import com.naturalautomation.selenium.element.ui.TableElement;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageFactory;

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

    @Given("I am in the $page page")
    public void givenUserInPage(@Named("page") String page) {
        LOG.info("Given the user is in the {} page.", page);
        testScope.put(CURRENT_PAGE, pageFactory.getPage(page).navigate());
    }

    @Given("I populate the page with random data")
    public void givenPagePopulatedRandom() {
        LOG.info("Given the page is populated with random data.");
        ((Page) testScope.get(CURRENT_PAGE)).fillDefaultData();
    }

    @Given("I have clicked on $element")
    public void givenIHaveClickedOn(@Named("element") String element) {
        ((Page) testScope.get(CURRENT_PAGE)).click(element);
    }

    @Given("I have written '$text' in the $field field")
    public void givenTextInFieldIsWritten(@Named("text") String text, @Named("field") String field) {
        ((Page) testScope.get(CURRENT_PAGE)).setFieldValue(field, text);
    }

    @Given("I have selected the '$option' option in the $field dropdown")
    public void givenSelectedOption(@Named("option") String option, @Named("field") String field) {
        ((Page) testScope.get(CURRENT_PAGE)).setFieldValue(field, option);
    }

    @Given("I have populated it with this value map: $examples")
    public void givenAddData(@Named("examples") ExamplesTable examples) {
        Page page = (Page) testScope.get(CURRENT_PAGE);
        testScope.put("examples", examples);
        page.importKeyValuePairsIntoFields(examples.getRows());
    }

    @Given("I have written these values: $examples")
    public void givenIHaveWrittenValues(@Named("examples") ExamplesTable examples) {
        Page page = (Page) testScope.get(CURRENT_PAGE);
        testScope.put("examples", examples);
        page.importNamedValuesIntoFields(examples.getHeaders(), examples.getRows());
    }

    @Given("I did a $action")
    public void givenIDidAction(@Named("action") String action) {
        testScope.put(CURRENT_PAGE, runAction(action));
    }

    @Given("I clicked table $table, on first row and column $column")
    public void givenIClickedOnTable(@Named("table") String table,
                                     @Named("column") String column) {
        ((TableElement) ((Page) testScope.get(CURRENT_PAGE)).getFieldInstance(table))
                .clickOnCellByRowAndColumnName(0, column);
    }

    @When("I $action")
    public void whenIDoAnAction(@Named("action") String action) {
        LOG.info("When the user does a {}.", action);
        testScope.put(CURRENT_PAGE, runAction(action));
    }

    @When("the $element is clicked")
    public void clickElement(@Named("element") String element){
        LOG.info("When the element {} is clicked",element);
        Page page = (Page) testScope.get(CURRENT_PAGE);
        page.click(element);
    }

    @Then("there should be $collectionName")
    public void thenThereShouldBeResults(@Named("collectionName") String collectionName) {
        LOG.info("Then there should be {}.", collectionName);
        Collection collection = (Collection) ((Page) testScope.get(CURRENT_PAGE)).getFieldInstance(collectionName);
        Assert.assertThat(collection, Matchers.notNullValue());
        Assert.assertThat(collection.size(), Matchers.greaterThan(0));
    }


    @Then("the element $element is visible")
    public void isVisible(@Named("element") String element){
        LOG.info("Then the element {} is visible.", element);
        Page page = (Page) testScope.get(CURRENT_PAGE);
        assert(page.validateIsVisible(element));
    }

    @Then("the $table has value '$value' in first row and column $column")
    public void tableHasValue(@Named("table") String table,
                            @Name("value") String value,
                            @Name("column") String column) {
        Page page = (Page) testScope.get(CURRENT_PAGE);
        assert(page.validateIfTableHasValue(table, 0, column, value));
    }

    @Then("just for show wait $seconds seconds")
    public void justForDemoWait(Integer seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Page runAction(String action) {
        return ((Page) testScope.get(CURRENT_PAGE)).invokeAction(action);
    }
}
