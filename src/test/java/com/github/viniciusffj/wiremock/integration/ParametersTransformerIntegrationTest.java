package com.github.viniciusffj.wiremock.integration;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.viniciusffj.wiremock.ParametersTransformer;
import io.restassured.RestAssured;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParametersTransformerIntegrationTest {

    private static class DummyExtension extends ResponseTransformer {
        private Parameters parameters;

        @Override
        public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
            this.parameters = parameters;
            return Response.Builder.like(response).build();
        }

        @Override
        public String getName() {
            return "dummy-extension";
        }

        @Override
        public boolean applyGlobally() {
            return false;
        }

        String getParameter(String name) {
            return this.parameters.getString(name);
        }
    }

    private static DummyExtension dummyExtension = new DummyExtension();

    @ClassRule
    public static WireMockClassRule service =
            new WireMockClassRule(options()
                    .port(8001)
                    .extensions(new ParametersTransformer(), dummyExtension));

    @Test
    public void shouldReplaceTransformerParameter() throws Exception {
        service.stubFor(post(urlEqualTo("/parameters-transformer"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withTransformerParameter("name", "${body-type=form,query=name}")
                        .withTransformerParameter("id", "123")
                        .withTransformers("parameters-transformer", "dummy-extension")));

        RestAssured
                .given()
                    .body("id=1&name=Pablo")
                .when()
                    .post("http://localhost:8001/parameters-transformer")
                .then()
                    .assertThat()
                    .statusCode(200);

        assertThat(dummyExtension.getParameter("name"), is("Pablo"));
        assertThat(dummyExtension.getParameter("id"), is("123"));

        RestAssured
                .given()
                    .body("id=1&name=Juan")
                .when()
                    .post("http://localhost:8001/parameters-transformer")
                .then()
                    .assertThat()
                    .statusCode(200);

        assertThat(dummyExtension.getParameter("name"), is("Juan"));
        assertThat(dummyExtension.getParameter("id"), is("123"));
    }
}
