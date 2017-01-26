package com.github.viniciusffj.wiremock;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.viniciusffj.wiremock.parameter.ParametersBuilder;

public class ParametersTransformer extends ResponseDefinitionTransformer {

    /*
    * We need to to this to always use the original transformer parameters value.
    * This is necessary due to a behavior in Wiremock[1], which changes the original
    * state of response definition
    *
    * [1] https://github.com/tomakehurst/wiremock/issues/592
    * */
    private static Parameters originalParams;

    @Override
    public ResponseDefinition transform(Request request,
                                        ResponseDefinition responseDefinition,
                                        FileSource files,
                                        Parameters parameters) {
        if (originalParams == null) {
            originalParams = (Parameters) parameters.clone();
        }

        ResponseDefinitionBuilder builder = ResponseDefinitionBuilder
                .like(responseDefinition)
                .but();

        return updateTransformerParameters(request, originalParams, builder)
                .build();
    }

    private ResponseDefinitionBuilder updateTransformerParameters(Request request, Parameters parameters, ResponseDefinitionBuilder builder) {
        Parameters newParameters = new ParametersBuilder(parameters).fromBody(request.getBodyAsString());

        for (String key : newParameters.keySet()) {
            builder = builder.withTransformerParameter(key, newParameters.get(key));
        }
        return builder;
    }

    @Override
    public String getName() {
        return "parameters-transformer";
    }

    @Override
    public boolean applyGlobally() {
        return false;
    }
}
