package com.fullstackmarc.fwkpoc.config;

import com.fullstackmarc.fwkpoc.selenium.TestScope;
import com.github.webdriverextensions.WebDriverExtensionsContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"com.fullstackmarc.selenium"})
public class AcceptanceTestsConfiguration {

    @Bean
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/pages/*.properties"));
        return ppc;
    }

    @Bean
    public WebDriver driver() throws IOException {
        WebDriverExtensionsContext.setDriver(new ChromeDriver());
        return WebDriverExtensionsContext.getDriver();
    }

    @Bean
    public TestScope testScope() {
        return new TestScope();
    }

}
