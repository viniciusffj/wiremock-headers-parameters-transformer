package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;

public class ParameterReplacer {
    private JsonBodyParser jsonBodyParser;

    public ParameterReplacer(String body) {
        this.jsonBodyParser = new JsonBodyParser(body);
    }

    public String newValue(String parameter) {
        ParameterParser parameterParser = new ParameterParser(parameter);

        if (parameterParser.hasAction()) {
            ReplaceAction action = parameterParser.action();
            Optional<String> requestParameter = this.jsonBodyParser.getValue(action.query());
            return requestParameter.or(parameter);
        }

        return parameter;
    }
}
