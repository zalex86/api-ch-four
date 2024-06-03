package com.tapyou.heplers;

import io.restassured.response.Response;

import java.util.Map;

public class Requests implements RestRequest {

    public Response getRequest(String url, Map<String, String> parameters) {
        return logResponse(givenWithRequestSpecBuilder(url)
                .params(parameters)
                .get());
    }
}
