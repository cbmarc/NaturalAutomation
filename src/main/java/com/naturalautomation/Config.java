package com.naturalautomation;

import com.naturalautomation.jbehave.WebDriverWrapper;
import com.naturalautomation.selenium.TestScope;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "com.naturalautomation")
public class Config {



    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }

    @Bean
    public TestScope testScope() {
        return new TestScope();
    }
    @Bean(destroyMethod = "quit")
    public WebDriverWrapper webDriverWrapper() throws IOException {
        return new WebDriverWrapper();
    }


}
