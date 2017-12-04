package com.naturalautomation.jbehave;

import com.naturalautomation.exceptions.NotInPageException;
import com.naturalautomation.exceptions.PageNotMappedException;
import com.naturalautomation.selenium.TestScope;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageFactory;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

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
    public void givenUserInPage(@Named("page") String page) throws PageNotMappedException, NotInPageException {
        LOG.info("Given the user is in the {} page.", page);
        testScope.put(CURRENT_PAGE, pageFactory.getPage(page).navigate());
    }

    @Given("I populate the page with random data")
    public void givenPagePopulatedRandom() {
        LOG.info("Given the page is populated with random data.");
        ((Page) testScope.get(CURRENT_PAGE)).fillDefaultData();
    }

    @Given("I have written '$text' in the $field field")
    public void givenTextInFieldIsWritten(@Named("text") String text, @Named("field") String field) throws NoSuchFieldException, IllegalAccessException {
        ((Page) testScope.get(CURRENT_PAGE)).setFieldValue(field, text);
    }

    @Given("I have selected the '$option' option in the $field dropdown")
    public void givenSelectedOption(@Named("option") String option, @Named("field") String field) throws NoSuchFieldException, IllegalAccessException {
        ((Page) testScope.get(CURRENT_PAGE)).setFieldValue(field, option);
    }

    @Given("I have populated it with this value map: $examples")
    public void givenAddData(@Named("examples") ExamplesTable examples) throws Exception {
        Page page = (Page) testScope.get(CURRENT_PAGE);
        testScope.put("examples", examples);
        Set<Exception> nestedExceptions = page.importKeyValuePairsIntoFields(examples.getRows());
        processNestedExceptions(nestedExceptions);
    }

    @Given("I have written these values: $examples")
    public void givenIHaveWrittenValues(@Named("examples") ExamplesTable examples) throws Exception {
        Page page = (Page) testScope.get(CURRENT_PAGE);
        testScope.put("examples", examples);
        Set<Exception> nestedExceptions = page.importNamedValuesIntoFields(examples.getHeaders(), examples.getRows());
        processNestedExceptions(nestedExceptions);
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

    private void processNestedExceptions(Set<Exception> nestedExceptions) throws Exception {
        if (nestedExceptions.size() > 0) {
            Exception e = new Exception("Several exceptions found when writing data into fields.");
            nestedExceptions.forEach(ne -> {
                Stream<StackTraceElement> a = Arrays.stream(e.getStackTrace());
                Stream<StackTraceElement> b = Arrays.stream(ne.getStackTrace());
                e.setStackTrace(Stream.concat(a, b).toArray(StackTraceElement[]::new));
            });
            throw e;
        }
    }
}
