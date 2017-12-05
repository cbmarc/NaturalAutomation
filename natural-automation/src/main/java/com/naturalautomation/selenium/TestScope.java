package com.naturalautomation.selenium;

import java.util.HashMap;
import java.util.Map;

public class TestScope {

    private Map<String, Object> cache = new HashMap<>();

    void reset() {
        cache.clear();
    }

    public Object get(String name) {
        return cache.get(name);
    }

    public void put(String name, Object object) {
        cache.put(name, object);
    }

    public Object remove(String name) {
        return cache.remove(name);
    }

}
