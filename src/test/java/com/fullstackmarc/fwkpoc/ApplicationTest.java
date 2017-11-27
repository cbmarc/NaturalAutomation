package com.fullstackmarc.fwkpoc;

import com.fullstackmarc.fwkpoc.selenium.pages.Named;
import com.fullstackmarc.fwkpoc.selenium.pages.PageFactory;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private PageFactory pageFactory;

        @Test
    public void pageMapDidLoad() throws Exception {
        Set<? extends Named> pages = pageFactory.getPages();
        Assert.assertThat(pages, Matchers.notNullValue());
        Assert.assertThat(pages.size(), Matchers.greaterThan(0));
    }

    @Test
    public void contextLoads() throws Exception {
    }
}
