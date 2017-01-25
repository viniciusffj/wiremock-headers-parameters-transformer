package com.github.viniciusffj.wiremock;

public class ReplaceAction {
    private String query;
    private BodyType type;

    public ReplaceAction(BodyType type, String query) {
        this.query = query;
        this.type = type;
    }

    public String query() {
        return this.query;
    }

    public BodyType type() {
        return this.type;
    }
}
