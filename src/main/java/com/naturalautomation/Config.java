package com.naturalautomation;

import com.naturalautomation.selenium.TestScope;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@SpringBootApplication
public class Config {

    private static final String DRIVER_NAME = "chromedriver";
    private static final String DRIVERS_PATH = "target/drivers/";
    private static final String DRIVER_PROPERTY = "webdriver.chrome.driver";

    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }

    @Bean
    public TestScope testScope() {
        return new TestScope();
    }

    @Bean(destroyMethod = "quit")
    public WebDriver driver() throws IOException {
        Path basePath = Paths.get(DRIVERS_PATH).toAbsolutePath();
        Optional<Path> fileName = Files.list(basePath).filter(p -> p.getFileName().toString().startsWith(DRIVER_NAME)).findFirst();
        fileName.ifPresent(path -> System.setProperty(DRIVER_PROPERTY, basePath + "/" + path.getFileName().toString()));
        return new ChromeDriver();
    }

}
