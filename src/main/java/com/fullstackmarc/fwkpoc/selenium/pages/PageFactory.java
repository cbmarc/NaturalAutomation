package com.fullstackmarc.fwkpoc.selenium.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PageFactory {

    private final Set<? extends Named> pages;

    @Autowired
    public <T extends Page & Named> PageFactory(Set<? extends T> pages) {
        this.pages = pages;
    }

    public Page getPage(String name) {
        Named namedPage = pages.stream().filter(p -> p.getName().equals(name)).findFirst().get();
        return (Page) namedPage;
    }
}
