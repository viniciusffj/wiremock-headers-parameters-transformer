package com.github.viniciusffj.wiremock.request;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FormBodyParserTest {

    private FormBodyParser body;

    @Before
    public void setUp() throws Exception {
        body = new FormBodyParser("id=123&name=Paul&url=http%3A%2F%2Flocalhost%3A8080%2F");
    }

    @Test
    public void shouldGetParameterIfPresent() throws Exception {
        Optional<String> value = body.getValue("id");

        assertThat(value.isPresent(), is(true));
        assertThat(value.get(), is("123"));
    }

    @Test
    public void shouldGetParameterInUtf8IfPresent() throws Exception {
        Optional<String> value = body.getValue("url");

        assertThat(value.isPresent(), is(true));
        assertThat(value.get(), is("http://localhost:8080/"));
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