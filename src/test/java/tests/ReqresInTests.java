package tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.models.RegisterBody;
import tests.models.RegisterResponse;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.specs.Specs.requestSpec;
import static tests.specs.Specs.response200Spec;

public class ReqresInTests {
    String userName = "morpheus";
    String userJobName = "leader";
    String userEmail = "eve.holt@reqres.in";
    String userPassword = "pistol";


    @Test
    @DisplayName("Check successful registration")
    void successfulRegisterTest() {
        RegisterBody requestBody = new RegisterBody();
        requestBody.setEmail(userEmail);
        requestBody.setPassword(userPassword);

        RegisterResponse response = step("Make a request", () -> given(requestSpec)
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

}
