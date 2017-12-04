package com.naturalautomation.reflection;

import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Optional;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static <T> Optional<Class<? extends T>> getWrapperClass(Class<T> iface) {
        Reflections reflections = new Reflections(iface.getPackage().getName());
        return reflections.getSubTypesOf(iface).stream()
                .filter(c -> !c.isInterface())
                .filter(c -> Arrays.stream(c.getInterfaces()).anyMatch(ci -> ci.equals(iface)))
                .findFirst();
    }

}
