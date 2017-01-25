package com.github.viniciusffj.wiremock.request;

import com.github.viniciusffj.wiremock.parameter.ParameterParser;
import com.github.viniciusffj.wiremock.parameter.ReplaceAction;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
        assertThat(action.original(), is("${body-type=json,query=$.team.name}"));
    }

    @Test
    public void shouldHaveActionWhenUsingFormBody() throws Exception {
        ParameterParser parameterParser = new ParameterParser("${body-type=form,query=param}");

        assertThat(parameterParser.hasAction(), is(true));
        ReplaceAction action = parameterParser.action();

        assertThat(action.type(), is(BodyType.FORM));
        assertThat(action.query(), is("param"));
        assertThat(action.original(), is("${body-type=form,query=param}"));
    }

    @Test
    public void shouldHaveActionWhenQueryIsInTheMiddleOfAText() throws Exception {
        String parameter = "{\"name\": \"${body-type=form,query=name}\"}";
        ParameterParser parameterParser = new ParameterParser(parameter);

        assertThat(parameterParser.hasAction(), is(true));
        ReplaceAction action = parameterParser.action();

        assertThat(action.type(), is(BodyType.FORM));
        assertThat(action.query(), is("name"));
        assertThat(action.original(), is("${body-type=form,query=name}"));
    }
}