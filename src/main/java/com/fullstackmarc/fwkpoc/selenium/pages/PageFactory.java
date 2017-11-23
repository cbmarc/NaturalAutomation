package com.fullstackmarc.fwkpoc.selenium.pages;

import com.fullstackmarc.fwkpoc.exceptions.PageNotMappedException;
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

    public Page getPage(String name) throws PageNotMappedException {
        Optional<? extends Named> optNamedPage = pages.stream().filter(p -> p.getName().equals(name)).findFirst();
        Named namedPage = optNamedPage.orElse(null);
        if (namedPage == null) {
            throw new PageNotMappedException(String.format("Page %s is not mapped. Check if a page extending the " +
                    "'Page' class and implementing the 'Named' interface exists.", name));
        }
        return (Page) namedPage;
    }

    public Set<? extends Named> getPages() {
        return pages;
    }
}
