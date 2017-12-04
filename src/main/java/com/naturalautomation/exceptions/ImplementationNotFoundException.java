package com.naturalautomation.exceptions;

public class ImplementationNotFoundException extends RuntimeException {

    public <T> ImplementationNotFoundException(Class<T> interfaceType) {
        super("Implementation not found for interface " + interfaceType.getName());
    }
}
