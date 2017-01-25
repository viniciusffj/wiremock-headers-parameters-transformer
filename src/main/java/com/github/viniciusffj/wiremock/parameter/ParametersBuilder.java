package com.github.viniciusffj.wiremock.parameter;

import com.github.tomakehurst.wiremock.extension.Parameters;

import java.util.Map;

public class ParametersBuilder {
    private Parameters parameters;

    public ParametersBuilder(Parameters parameters) {
        this.parameters = parameters;
    }

    public Parameters fromBody(String body) {
        Parameters parameters = (Parameters) this.parameters.clone();
        ParameterReplacer parameterReplacer = new ParameterReplacer(body);

        fromBody(parameters, parameterReplacer);

        return parameters;
    }

    private void fromBody(Map<String, Object> parameters, ParameterReplacer parameterReplacer) {
        for (String key: parameters.keySet()) {
            Object value = parameters.get(key);

            if (isAMap(value)) {
                fromBody((Map<String, Object>) value, parameterReplacer);
            }

            if (isAString(value)) {
                parameters.put(key, parameterReplacer.newValue((String) value));
            }
        }
    }

    private boolean isAMap(Object value) {
        return value instanceof Map;
    }

    private boolean isAString(Object value) {
        return value instanceof String;
    }
}
