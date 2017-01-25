package com.github.viniciusffj.wiremock.parameter;

import com.github.viniciusffj.wiremock.request.BodyParser;
import com.github.viniciusffj.wiremock.request.BodyType;
import com.github.viniciusffj.wiremock.request.FormBodyParser;
import com.github.viniciusffj.wiremock.request.JsonBodyParser;
import com.google.common.base.Optional;

import java.util.regex.Pattern;

public class ParameterReplacer {
    private final JsonBodyParser jsonBodyParser;
    private final FormBodyParser formBodyParser;

    public ParameterReplacer(String body) {
        this.jsonBodyParser = new JsonBodyParser(body);
        this.formBodyParser = new FormBodyParser(body);
    }

    public String newValue(String parameter) {
        ParameterParser parameterParser = new ParameterParser(parameter);

        if (parameterParser.hasAction()) {
            ReplaceAction action = parameterParser.action();
            Optional<String> requestParameter = bodyParser(action.type()).getValue(action.query());

            if (requestParameter.isPresent()) {
                return parameter.replaceAll(Pattern.quote(action.original()), requestParameter.get());
            }
        }

        return parameter;
    }

    private BodyParser bodyParser(BodyType type) {
        if (BodyType.JSON == type) {
            return this.jsonBodyParser;
        }

        return this.formBodyParser;
    }
}
