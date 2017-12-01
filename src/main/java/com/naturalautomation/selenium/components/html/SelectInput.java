package com.naturalautomation.selenium.components.html;

import org.openqa.selenium.support.ui.Select;

public class SelectInput extends HtmlComponent {

    public void chooseOption(String visibleText) {
        new Select(this)
            .selectByVisibleText(visibleText);
    }
}
