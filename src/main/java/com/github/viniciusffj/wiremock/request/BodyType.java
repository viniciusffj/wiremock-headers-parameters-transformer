package com.github.viniciusffj.wiremock.request;

public enum BodyType {
    JSON, FORM;

    public static BodyType fromString(String type) {
        String name = type ==  null ? "" : type.toUpperCase();
        return BodyType.valueOf(name);
    }
}
