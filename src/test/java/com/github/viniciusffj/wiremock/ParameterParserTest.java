package com.github.viniciusffj.wiremock;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParameterParserTest {

    @Test
    public void shouldNotHaveActionWhenNoQueryIsPassed() throws Exception {
        String parameter = "normal-value";
        ParameterParser parameterParser = new ParameterParser(parameter);

        assertThat(parameterParser.hasAction(), is(false));
    }

    @Test
    public void shouldHaveActionWhenCorrectQueryIsUsed() throws Exception {
        ParameterParser parameterParser = new ParameterParser("${body-type=json,query=$.team.name}");

        assertThat(parameterParser.hasAction(), is(true));
        ReplaceAction action = parameterParser.action();

        assertThat(action.type(), is(BodyType.JSON));
        assertThat(action.query(), is("$.team.name"));
    }
}