package com.naturalautomation.selenium.pages;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.naturalautomation.jbehave.WebDriverWrapper;
import com.naturalautomation.selenium.element.factory.ElementFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.naturalautomation.exceptions.PageNotMappedException;

@Component
public class PageFactory {

    private Map<String,Class<? extends Page>> map = new HashMap();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebDriverWrapper webDriverWrapper;


    public void register(String name, Class<? extends Page> clazz){
        map.put(name,clazz);
    }

    public Page getPage(String name) throws PageNotMappedException {
        if(!map.containsKey(name)){
            new PageNotMappedException(
                    String.format("Page %s is not mapped. Check if a page extending the " +
                            "'Page' class and implementing the 'Named' interface exists.", name));
        }

        return ElementFactory.initElements(webDriverWrapper.getWebDriver(), applicationContext.getBean(map.get(name)));
    }

}
