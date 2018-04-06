package com.naturalautomation.selenium.pages;

import com.naturalautomation.annotations.PageObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by rodrigo on 6/4/18.
 */
@Component
public class PageBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    PageFactory pageFactory;


    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if(o.getClass().isAnnotationPresent(PageObject.class) && o instanceof Named){
            pageFactory.register(((Named)o).getName(), (Class<? extends Page>) o.getClass());
        }
        return o;
    }
}
