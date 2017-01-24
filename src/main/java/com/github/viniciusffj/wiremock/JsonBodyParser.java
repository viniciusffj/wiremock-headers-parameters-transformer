package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;
import com.jayway.jsonpath.JsonPath;

public class JsonBodyParser {
    private String body;

    public JsonBodyParser(String body) {
        this.body = body;
        if (body == null ) {
            throw new IllegalArgumentException("Body can't be empty ");
        }
    }

    public Optional<String> getValue(String path) {
        Object value;
        try {
            value = JsonPath.read(body, path);
        } catch (Exception e) {
            return Optional.absent();
        }

        return Optional.of((String) value);
    }
}