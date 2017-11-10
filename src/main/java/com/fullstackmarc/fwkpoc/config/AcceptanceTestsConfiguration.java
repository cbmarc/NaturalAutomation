package com.fullstackmarc.fwkpoc.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"com.fullstackmarc.selenium"})
@PropertySources(@PropertySource("classpath:pages/*.properties"))
public class AcceptanceTestsConfiguration {

    @Bean("driver")
    public WebDriver driver() {
        return new ChromeDriver();
    }

}
