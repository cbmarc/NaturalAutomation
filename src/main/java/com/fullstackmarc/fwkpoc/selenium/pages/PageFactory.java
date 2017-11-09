package com.fullstackmarc.fwkpoc.selenium.pages;

import java.util.Map;

public class PageFactory {

    Map<String, ? extends AbstractPage> pageMap;

    public PageFactory(Map<String, ? extends AbstractPage> pageMap) {
        this.pageMap = pageMap;
    }
}
