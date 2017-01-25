package com.github.viniciusffj.wiremock.integration;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.github.viniciusffj.wiremock.HeadersTransformer;
import io.restassured.RestAssured;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadersTransformerIntegrationTest {

    private static class DummyExtension extends ResponseTransformer {
        private HttpHeaders headers;

        @Override
        public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
            this.headers = response.getHeaders();
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

        HttpHeader getHeader(String name) {
            return this.headers.getHeader(name);
        }
    }

    private static DummyExtension dummyExtension = new DummyExtension();

    @ClassRule
    public static WireMockClassRule service =
            new WireMockClassRule(options()
                    .port(8001)
                    .extensions(new HeadersTransformer(), dummyExtension));

    @Test
    public void shouldReplaceTransformerParameter() throws Exception {
        service.stubFor(post(urlEqualTo("/headers-transformer"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Location", "${body-type=json,query=url}")
                        .withHeader("Content-Type", "text/html")
                        .withTransformers("headers-transformer", "dummy-extension")));

        RestAssured
                .given()
                .body("{\"url\":\"http://www.palmeiras.com.br\"}")
                .when()
                .post("http://localhost:8001/headers-transformer")
                .then()
                .assertThat()
                .statusCode(200);

        assertThat(dummyExtension.getHeader("Location").values().get(0), is("http://www.palmeiras.com.br"));
        assertThat(dummyExtension.getHeader("Content-Type").values().get(0), is("text/html"));
    }
}
