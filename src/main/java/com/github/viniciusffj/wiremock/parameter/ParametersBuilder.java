package com.github.viniciusffj.wiremock.parameter;

import com.github.tomakehurst.wiremock.extension.Parameters;

import java.util.Map;

public class ParametersBuilder {
    private Parameters parameters;

    public ParametersBuilder(Parameters parameters) {
        this.parameters = parameters;
    }

    public Parameters fromBody(String body) {
        ParameterReplacer parameterReplacer = new ParameterReplacer(body);

        return fromBody(this.parameters, parameterReplacer);
    }

    private Parameters fromBody(Map<String, Object> parameters, ParameterReplacer parameterReplacer) {
        Parameters newParameters = new Parameters();

        for (String key: parameters.keySet()) {
            Object value = parameters.get(key);

            if (isAMap(value)) {
                value = fromBody((Map<String, Object>) value, parameterReplacer);
            }

            if (isAString(value)) {
                value = parameterReplacer.newValue((String) value);
            }

            newParameters.put(key, value);
        }

        return newParameters;
    }

    private boolean isAMap(Object value) {
        return value instanceof Map;
    }

    private boolean isAString(Object value) {
        return value instanceof String;
    }
}
