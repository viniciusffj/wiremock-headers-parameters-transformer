package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.viniciusffj.wiremock.header.HeadersBuilder;

public class HeadersTransformer extends ResponseDefinitionTransformer {
    @Override
    public ResponseDefinition transform(Request request,
                                        ResponseDefinition responseDefinition,
                                        FileSource files,
                                        Parameters parameters) {
        HttpHeaders headers = responseDefinition.getHeaders();

        headers = new HeadersBuilder(headers).fromBody(request.getBodyAsString());

        return ResponseDefinitionBuilder
                .like(responseDefinition)
                .but()
                    .withHeaders(headers)
                .build();
    }

    @Override
    public String getName() {
        return "headers-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
