package com.naturalautomation.exceptions;

public class ImplementationNotFoundException extends Exception {

    public <T> ImplementationNotFoundException(Class<T> interfaceType) {
        super("Implementation not found for interface " + interfaceType.getName());
    }
}
