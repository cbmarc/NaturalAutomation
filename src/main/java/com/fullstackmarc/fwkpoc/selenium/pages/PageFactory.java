package com.fullstackmarc.fwkpoc.selenium.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class PageFactory {

    private final Set<? extends Named> pages;

    @Autowired
    public <T extends Page & Named> PageFactory(Set<T> pages) {
        this.pages = pages;
    }

    public Page getPage(String name) {
        Optional<? extends Named> optNamedPage = pages.stream().filter(p -> p.getName().equals(name)).findFirst();
        Named namedPage = optNamedPage.orElse(null);
        return (Page) namedPage;
    }

    public Set<? extends Named> getPages() {
        return pages;
    }
}
