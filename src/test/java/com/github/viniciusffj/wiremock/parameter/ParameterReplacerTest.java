package com.github.viniciusffj.wiremock.parameter;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParameterReplacerTest {

    private ParameterReplacer parameterReplacer;

    @Before
    public void setUp() throws Exception {
        parameterReplacer = new ParameterReplacer("{\"id\": \"123\"}");
    }

    @Test
    public void shouldNotReplaceFinalParameter() throws Exception {
        String newValue = parameterReplacer.newValue("value");

        assertThat(newValue, is("value"));
    }

    @Test
    public void shouldReplaceParameterWhenBodyHasValue() throws Exception {
        String newValue = parameterReplacer.newValue("${body-type=json,query=$.id}");

        assertThat(newValue, is("123"));
    }

    @Test
    public void shouldReplaceParameterOnlyWhereRequestParameterIsNecessary() throws Exception {
        String parameter = "{\"id\": \"${body-type=json,query=$.id}\"}";
        String newValue = parameterReplacer.newValue(parameter);

        assertThat(newValue, is("{\"id\": \"123\"}"));
    }

    @Test
    public void shouldNotReplaceParameterWhenBodyHasNoValue() throws Exception {
        String newValue = parameterReplacer.newValue("${body-type=json,query=$.name}");

        assertThat(newValue, is("${body-type=json,query=$.name}"));
    }

    @Test
    public void shouldReplaceParameterForFormBody() throws Exception {
        ParameterReplacer parameterReplacer = new ParameterReplacer("id=123");

        String newValue = parameterReplacer.newValue("${body-type=form,query=id}");

        assertThat(newValue, is("123"));
    }
}