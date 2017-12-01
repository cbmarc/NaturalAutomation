package com.naturalautomation.selenium.pages.myfiddle;


import com.naturalautomation.selenium.components.html.HtmlComponent;
import com.naturalautomation.selenium.pages.InputData;
import com.naturalautomation.selenium.pages.Named;
import com.naturalautomation.selenium.pages.Page;
import com.naturalautomation.selenium.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@PageObject
public class MyFiddlePage extends Page implements Named {

    private static final String PAGE_NAME = "MyFiddle";
    private static final String PAGE_URL = "http://fiddle.jshell.net/smallpawn/dsnpc1nj/9/show/light/";

    @InputData
    @FindBy(id = "textone")
    private HtmlComponent textOne;

    @InputData
    @FindBy(id = "texttwo")
    private HtmlComponent textTwo;

    @FindBy(id = "btn-add")
    private HtmlComponent btnAdd;

    @FindBy(id = "stuff")
    private HtmlComponent stuff;

    @FindBy(className = "new-div")
    private List<HtmlComponent> additions;

    public MyFiddlePage add() {
        btnAdd.click();
        return this;
    }

    @Override
    public String getName() {
        return PAGE_NAME;
    }

    @Override
    protected boolean isInPage() {
        return textOne.isDisplayed() && textTwo.isDisplayed() && btnAdd.isDisplayed();
    }

    @Override
    protected String getURL() {
        return PAGE_URL;
    }

    @Override
    protected void selectIFrame() {
        getDriver().switchTo().defaultContent();
        List<WebElement> iframes = getDriver().findElements(By.tagName("iframe"));
        iframes.stream().findFirst().ifPresent(iframe -> getDriver().switchTo().frame(iframe));
    }
}
