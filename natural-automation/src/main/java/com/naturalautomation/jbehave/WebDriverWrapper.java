package com.naturalautomation.jbehave;

import org.openqa.selenium.WebDriver;

public class WebDriverWrapper {

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public void quit(){
        if(webDriver != null){
            webDriver.quit();
        }
    }
}
