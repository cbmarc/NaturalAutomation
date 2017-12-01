package com.naturalautomation.jbehave;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AbstractSpringJBehaveStories extends JUnitStories {

    @Autowired
    private ApplicationContext applicationContext;

    private CrossReference crossReference = new CrossReference()
            .withJsonOnly();

    private StepMonitor stepMonitor = new SilentStepMonitor();


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
                .useFailureStrategy(new FailingUponPendingStep())
                .useStepPatternParser(new RegexPrefixCapturingPatternParser("$"))
                .useStoryControls(new SpringStoryControls().doResetStateBeforeScenario(false))
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                .useStoryReporterBuilder(reporterBuilder)
                .useParameterConverters(parameterConverters)
                .useStepMonitor(stepMonitor);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Override
    protected List<String> storyPaths() {
        StoryFinder finder = new StoryFinder();
        String storyProperty = System.getProperty("story");
        String stories = "*.story";
        if (storyProperty != null && !storyProperty.isEmpty()) {
            stories = storyProperty + ".story";
        }
        return finder.findPaths(codeLocationFromClass(this.getClass()).getFile(), Arrays.asList("**/" + stories), Arrays.asList(""));
    }

}
