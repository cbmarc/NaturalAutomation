package com.naturalautomation.exceptions;

public class PageNotMappedException extends NaturalAutomationException {

    public PageNotMappedException() {
    }

    public PageNotMappedException(String s) {
        super(s);
    }

    public PageNotMappedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
