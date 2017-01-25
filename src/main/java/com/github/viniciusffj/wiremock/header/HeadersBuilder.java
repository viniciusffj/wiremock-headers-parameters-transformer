package com.github.viniciusffj.wiremock.header;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.viniciusffj.wiremock.parameter.ParameterReplacer;

import java.util.ArrayList;
import java.util.List;

public class HeadersBuilder {
    private HttpHeaders headers;

    public HeadersBuilder(HttpHeaders headers) {
        this.headers = headers;
    }

    public HttpHeaders fromBody(String body) {
        HttpHeaders newHeaders = new HttpHeaders();

        for (HttpHeader header: headers.all()) {
            HttpHeader httpHeader = new HttpHeader(header.key(), values(body, header));
            newHeaders = newHeaders.plus(httpHeader);
        }

        return newHeaders;
    }

    private List<String> values(String body, HttpHeader header) {
        List<String> values = new ArrayList<>();

        for (String value: header.values()) {
            values.add(new ParameterReplacer(body).newValue(value));
        }
        return values;
    }
}
