package com.naturalautomation.exceptions;

import com.naturalautomation.selenium.pages.Page;

public class NotInPageException extends NaturalAutomationException {
    private Page page;

    public NotInPageException(Page page) {
        this.page = page;
    }

    @Override
    public String getMessage() {
        return "Page with title '" + this.page.getTitle() + "' is not what I expected.";
    }
}
