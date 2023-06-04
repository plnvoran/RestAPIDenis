package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.models.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.specs.Specs.*;

public class ReqresInHWTests {

    String userName = "school 5";
    String userJob = "teacher";
    String userEmail = "eve.holt@reqres.in";
    String userPassword = "pistol";

    @Test
    @DisplayName("User has successfully registered")
    void successfulRegisterTest() {
        RegisterBody requestBody = new RegisterBody();
        requestBody.setEmail(userEmail);
        requestBody.setPassword(userPassword);

        RegisterResponse response = step("Make the request", () -> given(requestSpec)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .spec(response200Spec)
                .extract().as(RegisterResponse.class));

        step("Check user ID in response", () ->
                assertThat(response.getId()).isNotNull());

        step("Check token in response", () ->
                assertThat(response.getToken()).isNotNull());
    }

    @Test
    @DisplayName("User has registered unsuccessfully")
    void unsuccessfulRegistrationTest() {
        RegisterBodyWithoutPassword registerBodyWithoutPassword = new RegisterBodyWithoutPassword();
        registerBodyWithoutPassword.setEmail(userEmail);
        String expectedResponse = "Missing password";

        BadRequestResponse response = step("Make the request", () -> given(requestSpec)
                .body(registerBodyWithoutPassword)
                .when()
                .post("/register")
                .then()
                .spec(response400Spec)
                .extract().as(BadRequestResponse.class));

        step("Check the error message in the response", () ->
                assertThat(response.getError()).isEqualTo(expectedResponse));

    }

    @Test
    @DisplayName("Display page 2 of the user list and check user ids")
    void checkListUserIdsTest() {
        Integer expectedFirstUserId = 7;
        Integer expectedLastUserId = 12;
        Integer expectedListSize = 6;

        UsersResponse response = step("Make the request", () -> given(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(response200Spec)
                .extract().as(UsersResponse.class));

        step("Check ID for first user in the response", () ->
                assertThat(response.getData().getFirst().getId()).isEqualTo(expectedFirstUserId));

        step("Check the size of the list", () ->
                assertEquals(expectedListSize, response.getData().size()));

        step("Check ID for last user in the response", () ->
                assertThat(response.getData().getLast().getId()).isEqualTo(expectedLastUserId));
    }

    @Test
    @DisplayName("Successful user creation")
    void successfulUserCreationTest() {
        CreateUserBody requestBody = new CreateUserBody();
        requestBody.setName(userName);
        requestBody.setJob(userJob);

        CreateUserResponse response = step("Make the request", () -> given(requestSpec)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .spec(response201Spec)
                .extract().as(CreateUserResponse.class));

        step("Check user name in the response", () ->
                assertThat(response.getName()).isEqualTo(userName));

        step("Check user job in the response", () ->
                assertThat(response.getJob()).isEqualTo(userJob));
    }

    @Test
    @DisplayName("Checking for sending an unknown request")
    void unknownRequestTest() {
        BadRequestResponse response = step("Make the request", () -> given(requestSpec)
                .when()
                .get("unknown/23")
                .then()
                .spec(response404Spec)
                .extract().as(BadRequestResponse.class));

        step("Check the error in the response", () ->
                assertThat(response.getError()).isNull());
    }
}
