package com.naturalautomation;

import com.naturalautomation.annotations.UseWebDriver;
import com.naturalautomation.jbehave.AbstractSpringJBehaveStories;
import org.springframework.test.context.ContextConfiguration;

@UseWebDriver
@ContextConfiguration(classes = Config.class)
public class JBehaveStoriesIT extends AbstractSpringJBehaveStories {

}