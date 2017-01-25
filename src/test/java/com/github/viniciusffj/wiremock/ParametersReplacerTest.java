package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.extension.Parameters;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParametersReplacerTest {

    @Test
    public void shouldReplaceFirstLevelParameters() throws Exception {
        Parameters parameters = Parameters.one("url", "${body-type=json,query=$.url}");
        ParametersReplacer parametersReplacer = new ParametersReplacer(parameters);

        Parameters transformedParameters = parametersReplacer.fromBody("{\"url\": \"http://test.com\"}");

        assertThat(transformedParameters.getString("url"), is("http://test.com"));
    }

    @Test
    public void shouldReplaceNestedParameters() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>() {{
            Map<String, String> requestMap = new HashMap<String, String>() {{
                put("url", "${body-type=json,query=$.url}");
            }};

            put("request", requestMap);
        }};
        Parameters parameters = Parameters.from(map);
        ParametersReplacer parametersReplacer = new ParametersReplacer(parameters);

        Parameters transformedParameters = parametersReplacer.fromBody("{\"url\": \"http://test.com\"}");

        Map request = (Map) transformedParameters.get("request");
        assertThat((String) request.get("url"), is("http://test.com"));
    }
}