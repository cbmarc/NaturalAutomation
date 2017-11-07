package com.fullstackmarc.selenium.jbehave;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure()
@UsingEmbedder(ignoreFailureInStories = true, ignoreFailureInView = true)
@UsingSpring()
public class SpringInjectableEmbedder extends InjectableEmbedder {

    @Override
    public void run() throws Throwable {
        injectedEmbedder().runStoriesAsPaths(storyPaths());
    }

    private List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(
                        codeLocationFromClass(
                                this.getClass()).getFile(),
                        Arrays.asList("**/*.story"),
                        Arrays.asList(""));
    }


}
