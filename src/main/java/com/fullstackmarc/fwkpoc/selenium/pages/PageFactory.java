package com.fullstackmarc.fwkpoc.selenium.pages;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageFactory {

    Map<String, ? extends AbstractPage> pageMap;

    public PageFactory(Map<String, ? extends AbstractPage> pageMap) {
        this.pageMap = pageMap;
    }
}
