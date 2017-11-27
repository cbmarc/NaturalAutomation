package com.fullstackmarc.fwkpoc.jbehave;

import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class LoggingStoryReporter implements StoryReporter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingStoryReporter.class);

    @Override
    public void successful(String step) {
        LOG.info("Step {} run successful.", step);
    }

    @Override
    public void failed(String step, Throwable cause) {
        LOG.error("Step " + step + " failed.", cause.getCause());
    }

    @Override
    public void storyNotAllowed(Story story, String filter) {

    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {

    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        LOG.info("Currently running story {}.", story.getName());
    }

    @Override
    public void afterStory(boolean givenOrRestartingStory) {

    }

    @Override
    public void narrative(Narrative narrative) {

    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {

    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {

    }

    @Override
    public void beforeScenario(String scenarioTitle) {
        LOG.info("Currently running scenario {}.", scenarioTitle);
    }

    @Override
    public void scenarioMeta(Meta meta) {

    }

    @Override
    public void afterScenario() {

    }

    @Override
    public void givenStories(GivenStories givenStories) {

    }

    @Override
    public void givenStories(List<String> storyPaths) {

    }

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {

    }

    @Override
    public void example(Map<String, String> tableRow) {

    }

    @Override
    public void afterExamples() {

    }

    @Override
    public void beforeStep(String step) {

    }



    @Override
    public void ignorable(String step) {

    }

    @Override
    public void comment(String step) {

    }

    @Override
    public void pending(String step) {

    }

    @Override
    public void notPerformed(String step) {

    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {

    }

    @Override
    public void restarted(String step, Throwable cause) {

    }

    @Override
    public void restartedStory(Story story, Throwable cause) {

    }

    @Override
    public void dryRun() {

    }

    @Override
    public void pendingMethods(List<String> methods) {

    }
}
