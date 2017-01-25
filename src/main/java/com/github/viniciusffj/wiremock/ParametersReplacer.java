package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import com.google.common.base.Optional;

import java.util.Map;

public class ParametersReplacer {
    private Parameters parameters;

    public ParametersReplacer(Parameters parameters) {
        this.parameters = parameters;
    }

    public Parameters fromBody(String body) {
        Parameters parameters = (Parameters) this.parameters.clone();

        fromBody(body, parameters);

        return parameters;
    }

    private void fromBody(String body, Map<String, Object> parameters) {
        for (String key: parameters.keySet()) {
            Object value = parameters.get(key);

            if (isAMap(value)) {
                fromBody(body, (Map<String, Object>) value);
            }

            if (isAString(value)) {
                parameters.put(key, newValue(body, (String) value));
            }
        }
    }

    private boolean isAMap(Object value) {
        return value instanceof Map;
    }

    private String newValue(String body, String value) {
        ParameterParser parameterParser = new ParameterParser(value);

        if (parameterParser.hasAction()) {
            String query = parameterParser.action().query();
            Optional<String> requestParameter = new JsonBodyParser(body).getValue(query);
            return requestParameter.or(value);
        }

        return value;
    }

    private boolean isAString(Object value) {
        return value instanceof String;
    }
}
