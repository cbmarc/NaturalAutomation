package com.naturalautomation.exceptions;

public class ImplementationNotFoundException extends NaturalAutomationException {
    public ImplementationNotFoundException() {
    }

    public ImplementationNotFoundException(String s) {
        super(s);
    }

    public ImplementationNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ImplementationNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public ImplementationNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public <T> ImplementationNotFoundException(Class<T> interfaceType) {
        super("Implementation not found for interface " + interfaceType.getName());
    }
}
