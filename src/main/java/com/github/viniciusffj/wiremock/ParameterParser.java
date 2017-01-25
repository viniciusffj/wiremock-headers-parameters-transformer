package com.github.viniciusffj.wiremock;

import com.github.viniciusffj.wiremock.request.BodyType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterParser {
    private static final int TYPE_CAPTURE_GROUP = 1;
    private static final int QUERY_CAPTURE_GROUP = 2;
    private String parameter;

    private static Pattern REPLACE_PATTERN = Pattern.compile("\\$\\{body-type=(.*),query=(.*)}");
    private Matcher matcher;


    public ParameterParser(String parameter) {
        this.parameter = parameter;
    }

    public boolean hasAction() {
        matcher = REPLACE_PATTERN.matcher(this.parameter);
        return matcher.find();
    }

    public ReplaceAction action() {
        String query = this.matcher.group(QUERY_CAPTURE_GROUP);
        return new ReplaceAction(getType(), query);
    }

    private BodyType getType() {
        return BodyType.fromString(this.matcher.group(TYPE_CAPTURE_GROUP));
    }
}
