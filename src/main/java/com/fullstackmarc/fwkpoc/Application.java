package com.fullstackmarc.fwkpoc;

import com.fullstackmarc.fwkpoc.selenium.TestScope;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.Scope;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@SpringBootApplication
public class Application {

    @Bean
    public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/pages/*.properties"));
        return ppc;
    }

    @Bean(destroyMethod = "quit")
    public WebDriver driver() throws IOException {
        Path basePath = Paths.get("target/").toAbsolutePath();
        System.setProperty("webdriver.chrome.driver", basePath + "/drivers/chromedriver.exe");
        return new ChromeDriver();
    }

    @Bean
    public Scope testScope() {
        return new TestScope();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.registerShutdownHook();
    }

}
