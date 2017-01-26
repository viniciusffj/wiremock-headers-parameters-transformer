package com.github.viniciusffj.wiremock.parameter;

import com.github.tomakehurst.wiremock.extension.Parameters;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class ParametersBuilderTest {

    @Test
    public void shouldReplaceFirstLevelParameters() throws Exception {
        Parameters parameters = Parameters.one("url", "${body-type=json,query=$.url}");
        ParametersBuilder parametersBuilder = new ParametersBuilder(parameters);

        Parameters transformedParameters = parametersBuilder.fromBody("{\"url\": \"http://test.com\"}");

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
        ParametersBuilder parametersBuilder = new ParametersBuilder(parameters);

        Parameters transformedParameters = parametersBuilder.fromBody("{\"url\": \"http://test.com\"}");

        Map request = (Map) transformedParameters.get("request");
        assertThat((String) request.get("url"), is("http://test.com"));
    }
}