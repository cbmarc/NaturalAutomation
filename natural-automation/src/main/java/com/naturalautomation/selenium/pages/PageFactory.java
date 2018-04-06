package com.naturalautomation.selenium.pages;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.naturalautomation.exceptions.PageNotMappedException;

@Component
public class PageFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public <T extends Page & Named> PageFactory(Set<T> pages) {
        this.pages = pages;
    }

    public Page getPage(String name) throws PageNotMappedException {
        Optional<? extends Named> namedPage = pages.stream().filter(p -> p.getName().equals(name)).findFirst();

        return (Page) namedPage
                .orElseThrow(() ->
                        new PageNotMappedException(
                                String.format("Page %s is not mapped. Check if a page extending the " +
                                        "'Page' class and implementing the 'Named' interface exists.", name)));
    }

}
