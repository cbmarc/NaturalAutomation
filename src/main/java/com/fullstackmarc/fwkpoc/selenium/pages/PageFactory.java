package com.fullstackmarc.fwkpoc.selenium.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class PageFactory {

    private final Set<? extends Named> pageMap;

    @Autowired
    public <T extends Page & Named> PageFactory(Set<? extends T> pageMap) {
        this.pageMap = pageMap;
    }

    public Page getPage(String name) {
        Named namedPage = pageMap.stream().filter(p -> p.getName().equals(name)).findFirst().get();
        return (Page) namedPage;
    }
}
