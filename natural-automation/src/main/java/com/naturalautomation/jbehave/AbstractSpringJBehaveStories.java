package com.naturalautomation.jbehave;

import com.naturalautomation.annotations.PageComponentScan;
import com.naturalautomation.annotations.PageObject;
import com.naturalautomation.annotations.UseWebDriver;
import com.naturalautomation.exceptions.NaturalAutomationException;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageFactory;
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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
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

    @Autowired
    private PageFactory pageFactory;

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
            preparePages();
        } catch (IOException e) {
            throw new NaturalAutomationException("Error creating selenium driver",e);
        }
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    private void preparePages() {
        if(!this.getClass().isAnnotationPresent(PageComponentScan.class)){
            throw new NaturalAutomationException("There is not PageComponentScan annotation defined in the code. Please define this annotation.");
        }
        PageComponentScan pageComponentScan = this.getClass().getAnnotation(PageComponentScan.class);
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(PageObject.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(pageComponentScan.basePackage())) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(beanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                // if base package is too generic could lead to errors.
            }
            if(clazz.isAnnotationPresent(PageObject.class) && Page.class.isAssignableFrom(clazz)) {
                System.out.println("Bean definition: " + beanDefinition.getBeanClassName());
                PageObject pageObject = clazz.getAnnotation(PageObject.class);
                if("".equals(pageObject.name())){
                    throw new NaturalAutomationException(String.format("The class %s has a default name. Please set up a proper name for this page.",clazz.getSimpleName()));
                }
                pageFactory.register(pageObject.name(), (Class<? extends Page>) clazz);
            }
        }
    }

    private void prepareWebDriver() throws IOException {
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
