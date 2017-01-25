package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormBodyParser {
    private String body;

    public FormBodyParser(String body) {
        this.body = body;
    }

    public Optional<String> getValue(String parameter) {
        Pattern pattern = Pattern.compile(String.format("%s=([^=&]*)", parameter));

        Matcher matcher = pattern.matcher(this.body);

        if (matcher.find()) {
            return Optional.fromNullable(matcher.group(1));
        }

        return Optional.absent();
    }
}
