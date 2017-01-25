package com.github.viniciusffj.wiremock;

public enum BodyType {
    JSON;

    public static BodyType fromString(String type) {
        String name = type ==  null ? "" : type.toUpperCase();
        return BodyType.valueOf(name);
    }
}
