package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;

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
            return requestParameter.or(parameter);
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
