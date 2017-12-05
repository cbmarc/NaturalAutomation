package com.naturalautomation.jbehave;

import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.annotations.UseWebDriver;
import com.naturalautomation.exceptions.NaturalAutomationException;
import com.naturalautomation.selenium.element.factory.ElementFactory;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.configuration.spring.SpringStoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@org.springframework.context.annotation.Configuration
public class AbstractSpringJBehaveStories extends JUnitStories {

    @Autowired
    private ApplicationContext applicationContext;

    private CrossReference crossReference = new CrossReference()
            .withJsonOnly();

    private StepMonitor stepMonitor = new SilentStepMonitor();

    @Autowired
    private WebDriverWrapper webDriverWrapper;


    public AbstractSpringJBehaveStories() {

    }

    @Override
    public Configuration configuration() {
        // Start from default ParameterConverters instance
        ParameterConverters parameterConverters = new ParameterConverters();
        StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
                .withReporters(new LoggingStoryReporter())
                .withCodeLocation(CodeLocations.codeLocationFromClass(this.getClass()))
                .withDefaultFormats()
                .withFailureTrace(true)
                .withFailureTraceCompression(true)
                .withDefaultFormats()
                .withCrossReference(crossReference);

        return new MostUsefulConfiguration()
                .useFailureStrategy( new FailingUponPendingStep() )
                .useStepPatternParser( new RegexPrefixCapturingPatternParser( "$" ) )
                .useStoryControls( new SpringStoryControls().doResetStateBeforeScenario( false ) )
                .useStoryLoader( new LoadFromClasspath( this.getClass() ) )
                .useStoryReporterBuilder( reporterBuilder )
                .useParameterConverters( parameterConverters )
                .useStepMonitor( stepMonitor );
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        try {
            prepareWebDriver();
        } catch (IOException e) {
            new NaturalAutomationException("Error creating selenium driver",e);
        }
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    private void prepareWebDriver() throws IOException {
        initiliazeDriver();
        initializePageObjects();

    }

    private void initializePageObjects() {
        for (String name : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(name);
            if (bean.getClass().isAnnotationPresent(PageObject.class)) {
                ElementFactory.initElements(webDriverWrapper.getWebDriver(), bean);
            }
        }
    }

    private void initiliazeDriver() throws IOException {
        if(this.getClass().isAnnotationPresent(UseWebDriver.class)) {
            Path basePath = Paths.get(DRIVERS_PATH).toAbsolutePath();
            Optional<Path> fileName = Files.list(basePath).filter(p -> p.getFileName().toString().startsWith(DRIVER_NAME)).findFirst();
            fileName.ifPresent(path -> System.setProperty(DRIVER_PROPERTY, basePath + "/" + path.getFileName().toString()));
            webDriverWrapper.setWebDriver(new ChromeDriver());
        }
    }

    @Override
    protected List<String> storyPaths() {
        StoryFinder finder = new StoryFinder();
        String storyProperty = System.getProperty("story");
        String stories = "*.story";
        if (storyProperty != null && !storyProperty.isEmpty()) {
            stories = storyProperty + ".story";
        }
        return finder.findPaths(CodeLocations.codeLocationFromClass(this.getClass()).getFile(), Arrays.asList("**/" + stories), Arrays.asList(""));
    }

    private static final String DRIVER_NAME = "chromedriver";
    private static final String DRIVERS_PATH = "target/drivers/";
    private static final String DRIVER_PROPERTY = "webdriver.chrome.driver";



}
