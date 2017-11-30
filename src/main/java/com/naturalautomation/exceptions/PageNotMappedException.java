package com.naturalautomation.exceptions;

public class PageNotMappedException extends Exception {

    public PageNotMappedException() {
    }

    public PageNotMappedException(String s) {
        super(s);
    }

    public PageNotMappedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
