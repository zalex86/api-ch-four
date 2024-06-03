package com.tapyou.heplers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public interface RestRequest {
    default RequestSpecification givenWithRequestSpecBuilder(String baseUrl) {
        return given().spec(new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured()).build());
    }

    default Response logResponse(Response response) {
        return response.then()
                .log().all()
                .extract().response();
    }
}
