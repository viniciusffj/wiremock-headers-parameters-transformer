package com.github.viniciusffj.wiremock;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HeadersTransformerTest {

    @Test
    public void shouldRegisterCorrectName() throws Exception {
        HeadersTransformer parametersTransformer = new HeadersTransformer();

        assertThat(parametersTransformer.getName(), is("headers-transformer"));
    }

    @Test
    public void shouldNotBeAppliedGlobally() throws Exception {
        HeadersTransformer parametersTransformer = new HeadersTransformer();

        assertThat(parametersTransformer.applyGlobally(), is(false));
    }

}