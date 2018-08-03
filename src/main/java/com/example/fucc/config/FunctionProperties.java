package com.example.fucc.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FunctionProperties {
    private static final String BUNDLE_NAME = "function";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    public FunctionProperties() {
    }

    public static String getString(String key){
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "error";
        }
    }
}