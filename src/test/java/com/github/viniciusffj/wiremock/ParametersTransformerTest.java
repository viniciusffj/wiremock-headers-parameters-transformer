package com.github.viniciusffj.wiremock;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParametersTransformerTest {

    @Test
    public void shouldRegisterCorrectName() throws Exception {
        ParametersTransformer parametersTransformer = new ParametersTransformer();

        assertThat(parametersTransformer.getName(), is("parameters-transformer"));
    }

    @Test
    public void shouldNotBeAppliedGlobally() throws Exception {
        ParametersTransformer parametersTransformer = new ParametersTransformer();

        assertThat(parametersTransformer.applyGlobally(), is(false));
    }
}