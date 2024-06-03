package com.tapyou.heplers;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

public interface TestUtils {
    static Response verifyCode(Response response, Integer statusCode) {
        return response.then().statusCode(statusCode).extract().response();
    }

    static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }
}
