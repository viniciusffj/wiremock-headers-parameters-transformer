package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonBodyParserTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowNullPointerExceptionWhenBodyIsNull() throws Exception {
        new JsonBodyParser(null);
    }

    @Test
    public void shouldGetRootPropertyValue() throws Exception {
        JsonBodyParser bodyParser = new JsonBodyParser("{\"key\": \"value\"}");

        Optional<String> value = bodyParser.getValue("$.key");

        assertThat(value.isPresent(), is(true));
        assertThat(value.get(), is("value"));
    }

    @Test
    public void shouldReturnNotPresentValueWhenPathIsInvalid() throws Exception {
        JsonBodyParser bodyParser = new JsonBodyParser("");

        Optional<String> value = bodyParser.getValue("$.");

        assertThat(value.isPresent(), is(false));
    }

    @Test
    public void shouldReturnNotPresentValueWhenPropertyIsNull() throws Exception {
        JsonBodyParser bodyParser = new JsonBodyParser("{\"key\": \"value\"}");

        Optional<String> value = bodyParser.getValue(null);

        assertThat(value.isPresent(), is(false));
    }

    @Test
    public void shouldReturnNotPresentValueWhenPropertyDoesNotExist() throws Exception {
        JsonBodyParser bodyParser = new JsonBodyParser("{\"key\": \"value\"}");

        Optional<String> value = bodyParser.getValue("$.property");

        assertThat(value.isPresent(), is(false));
    }

    @Test
    public void shouldReturnValueForNestedProperty() throws Exception {
        JsonBodyParser bodyParser = new JsonBodyParser("{\"person\": { \"name\": \"Pablo\"} }");

        Optional<String> value = bodyParser.getValue("$.person.name");

        assertThat(value.isPresent(), is(true));
        assertThat(value.get(), is("Pablo"));

    }
}