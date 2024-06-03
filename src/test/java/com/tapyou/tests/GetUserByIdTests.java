package com.tapyou.tests;

import com.tapyou.BaseSetUp;
import com.tapyou.heplers.data.User;
import com.tapyou.heplers.data.UserIdListResponse;
import com.tapyou.heplers.data.UserResponse;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.jayway.jsonassert.JsonAssert.with;
import static com.tapyou.heplers.TestUtils.verifyCode;
import static com.tapyou.heplers.data.enums.Gender.female;
import static com.tapyou.heplers.data.enums.Gender.male;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("User tests")
@Story("GET /api/test/users")
public class GetUserByIdTests extends BaseSetUp {

    private static List<Integer> maleIdList;
    private static List<Integer> femaleIdList;

    @BeforeClass
    public void getUsersLists() {
        maleIdList = userHelper.getUsersWithVerify(male.name()).as(UserIdListResponse.class).getIdList();
        femaleIdList = userHelper.getUsersWithVerify(female.name()).as(UserIdListResponse.class).getIdList();
    }

    @DataProvider(name = "positive")
    public static Object[][] positive() {
        List<Object[]> combinedList = new ArrayList<>();
        for (Integer maleId : maleIdList) {
            combinedList.add(new Object[]{maleId, male.name()});
        }
        for (Integer femaleId : femaleIdList) {
            combinedList.add(new Object[]{femaleId, female.name()});
        }
        return combinedList.toArray(new Object[0][]);
    }
    @Test(description = "Get user by id positive test", dataProvider = "positive")
    public void getUserByIdTest(Integer id, String gender) {
        UserResponse userModel = new UserResponse()
                .setErrorCode(0)
                .setIsSuccess(true)
                .setUser(new User()
                        .setGender(gender)
                        .setId(id));

        Response response = verifyCode(userHelper.getUserById(String.valueOf(id)), SC_OK);
        assertThat("User Json Scheme Assertion", response.getBody().asString(),
                matchesJsonSchemaInClasspath("jsonSchemes/user.json"));

        var userResponse = response.as(UserResponse.class);

        Assertions.assertThat(userResponse).usingRecursiveComparison()
                .comparingOnlyFields("errorCode", "isSuccess", "user.gender", "user.id")
                .isEqualTo(userModel);
    }

    @DataProvider(name = "negativeIds")
    public static String[] negativeIds() {
        return new String[]{
                "",
                "0",
                "10,11",
        };
    }

    @Test(description = "Get user by id border test", dataProvider = "negativeIds")
    public void getUserByIdNegativeTest(String ids) {
        Response response = verifyCode(userHelper.getUserById(ids), SC_BAD_REQUEST);
        assertThat("User Json Scheme Assertion", response.getBody().asString(),
                matchesJsonSchemaInClasspath("jsonSchemes/user.json"));

        with(response.asString())
                .assertThat("$.isSuccess", Matchers.is(false));
    }

    @Test(description = "Get user by not existing id test")
    public void getUserByNotExistingIdTest() {
        List<Integer> idList = new ArrayList<>();
        idList.addAll(maleIdList);
        idList.addAll(femaleIdList);
        int maxId= idList.stream().max(Comparator.naturalOrder()).orElseThrow();

        Response response = verifyCode(userHelper.getUserById(String.valueOf(maxId+1)), SC_BAD_REQUEST);
        assertThat("User Json Scheme Assertion", response.getBody().asString(),
                matchesJsonSchemaInClasspath("jsonSchemes/user.json"));
        with(response.asString())
                .assertThat("$.isSuccess", Matchers.is(false));

    }

}
