package com.tapyou.heplers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.tapyou.BaseSetUp.SERVER;
import static org.apache.http.HttpStatus.SC_OK;

public class UserHelper {

    public static final String API_TEST = "api/test/";
    public static final String USERS = "users";

    @Step("Get users list by gender: {0} with code 200 GET /api/test/users")
    public Response getUsersWithVerify(String gender) {
        var params = new HashMap<String, String>();
        params.put("gender", gender);
        return TestUtils.verifyCode(new Requests().getRequest(SERVER + API_TEST + USERS, params), SC_OK);
    }

    @Step("Get users list by gender: {0} GET /api/test/users")
    public Response getUsers(String gender) {
        var params = new HashMap<String, String>();
        params.put("gender", gender);
        return new Requests().getRequest(SERVER + API_TEST + USERS, params);
    }

    @Step("Get user by id: {0} GET /api/test/user/{id}")
    public Response getUserById(String id) {
        return new Requests().getRequest(SERVER + API_TEST + "user/" + id, new HashMap<>());
    }
}
