package com.naturalautomation.exceptions;

public class NaturalAutomationException extends RuntimeException {

    public NaturalAutomationException() {
    }

    public NaturalAutomationException(String s) {
        super(s);
    }

    public NaturalAutomationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NaturalAutomationException(Throwable throwable) {
        super(throwable);
    }

    public NaturalAutomationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

}
