package com.fullstackmarc.fwkpoc.selenium;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;

public class TestScope implements Scope {

    private Map<String, Object> cache = new HashMap<>();

    public void reset() {
        cache.clear();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (!cache.containsKey(name)) {
            cache.put(name, objectFactory.getObject());
        }
        return cache.get(name);
    }

    @Override
    public Object remove(String name) {
        return cache.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
