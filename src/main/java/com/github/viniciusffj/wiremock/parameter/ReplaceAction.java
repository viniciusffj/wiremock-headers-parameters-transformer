package com.github.viniciusffj.wiremock.parameter;

import com.github.viniciusffj.wiremock.request.BodyType;

public class ReplaceAction {
    private String query;
    private BodyType type;
    private String original;

    public ReplaceAction(BodyType type, String query, String original) {
        this.query = query;
        this.type = type;
        this.original = original;
    }

    public String query() {
        return this.query;
    }

    public BodyType type() {
        return this.type;
    }

    public String original() {
        return this.original;
    }
}
