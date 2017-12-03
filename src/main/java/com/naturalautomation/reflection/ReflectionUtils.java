package com.naturalautomation.reflection;

import org.reflections.Reflections;

import java.util.Optional;
import java.util.Set;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static <T> Class<?> getWrapperClass(Class<T> iface) {
        Reflections reflections = new Reflections(iface.getPackage().getName());
        Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(iface);
        Optional<Class<? extends T>> firstCandidate = subTypes.stream().findFirst();
        return firstCandidate.orElse(null);
    }

}
