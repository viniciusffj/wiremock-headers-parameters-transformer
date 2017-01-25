package com.github.viniciusffj.wiremock.header;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadersBuilderTest {

    @Test
    public void shouldReplaceFirstLevelParameters() throws Exception {
        HttpHeader content = new HttpHeader("Content", "value");
        HttpHeader location = new HttpHeader("Location", "${body-type=form,query=url}");
        HttpHeaders httpHeaders = new HttpHeaders(content, location);

        HeadersBuilder parametersBuilder = new HeadersBuilder(httpHeaders);

        HttpHeaders transformedParameters = parametersBuilder.fromBody("url=http://test.com");

        assertThat(transformedParameters.getHeader("Content").values().get(0), is("value"));
        assertThat(transformedParameters.getHeader("Location").values().get(0), is("http://test.com"));
    }

    @Test
    public void shouldReplaceMultiValueHeader() throws Exception {
        List<String> values = Arrays.asList("${body-type=form,query=id}", "type", "${body-type=form,query=name}");
        HttpHeader location = new HttpHeader("some_header", values);
        HttpHeaders httpHeaders = new HttpHeaders(location);

        HeadersBuilder parametersBuilder = new HeadersBuilder(httpHeaders);

        HttpHeaders transformedParameters = parametersBuilder.fromBody("id=123&name=Juan");

        assertThat(transformedParameters.getHeader("some_header").values().get(0), is("123"));
        assertThat(transformedParameters.getHeader("some_header").values().get(1), is("type"));
        assertThat(transformedParameters.getHeader("some_header").values().get(2), is("Juan"));
    }

}