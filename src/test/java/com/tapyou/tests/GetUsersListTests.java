package com.tapyou.tests;

import com.tapyou.BaseSetUp;
import com.tapyou.heplers.data.UserIdListResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.tapyou.heplers.TestUtils.getRandomString;
import static com.tapyou.heplers.TestUtils.verifyCode;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("User tests")
@Story("GET /api/test/user/{id}")
public class GetUsersListTests extends BaseSetUp {

    @DataProvider(name = "positive")
    public static String[] positive() {
        return new String[]{
                "male",
                "female"
        };
    }

    @Test(description = "Get users positive test", dataProvider = "positive")
    public void getUsersTest(String gender) {
        UserIdListResponse userIdListResponseModel = new UserIdListResponse()
                .setErrorCode(0)
                .setIsSuccess(true);

        Response response = userHelper.getUsersWithVerify(gender);
        assertThat("User Json Scheme Assertion", response.getBody().asString(),
                matchesJsonSchemaInClasspath("jsonSchemes/users.json"));
        var userIdListResponse = response.as(UserIdListResponse.class);

        Assertions.assertThat(userIdListResponse).usingRecursiveComparison()
                .ignoringFields("idList")
                .isEqualTo(userIdListResponseModel);
        Assertions.assertThat(userIdListResponse.getIdList())
                .hasSizeGreaterThanOrEqualTo(1);
    }

    @DataProvider
    public static String[] negative() {
        return new String[]{
                "Male",
                "Female",
                "",
                " ",
                "males",
                "females",
                "%",
                "*",
                "_",
                "^",
                "!",
                "/",
                "0",
                "f",
                "m",
                getRandomString(6)
        };
    }

    @Test(description = "Get users negative test", dataProvider = "negative")
    public void getUsersNegativeTest(String gender) {
        Response response = verifyCode(userHelper.getUsers(gender), SC_BAD_REQUEST);
        assertThat("User Json Scheme Assertion", response.getBody().asString(),
                matchesJsonSchemaInClasspath("jsonSchemes/users.json"));

        UserIdListResponse userIdListResponse = response.as(UserIdListResponse.class);
        Assertions.assertThat(userIdListResponse.getIsSuccess()).isFalse();
    }


}
