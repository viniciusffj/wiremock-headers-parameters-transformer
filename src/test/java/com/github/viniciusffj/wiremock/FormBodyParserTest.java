package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FormBodyParserTest {

    private FormBodyParser body;

    @Before
    public void setUp() throws Exception {
        body = new FormBodyParser("id=123&name=Paul");
    }

    @Test
    public void shouldGetParameterIfPresent() throws Exception {
        Optional<String> value = body.getValue("id");

        assertThat(value.isPresent(), is(true));
        assertThat(value.get(), is("123"));
    }

    @Test
    public void shouldNotGetParameterIfNotPresent() throws Exception {
        Optional<String> value = body.getValue("lastName");

        assertThat(value.isPresent(), is(false));
    }

    @Test
    public void shouldNotGetParameterIfNull() throws Exception {
        Optional<String> value = body.getValue(null);

        assertThat(value.isPresent(), is(false));
    }
}