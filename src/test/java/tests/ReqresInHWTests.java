package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ReqresInHWTests {

   @BeforeEach
   void setupUriAndPath() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
   }

    @Test
    @DisplayName("User has successfully registered")
    void successfulRegistrationTest() {

        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("User has registered unsuccessfully")
    void unsuccessfulRegistrationTest() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\"}"; // BAD PRACTICE

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));

    }

    @Test
    @DisplayName("Display page 2 of the user list and check its structure")
    void checkStructureOfUserListTest() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemes/list-users-response-scheme.json"));

    }

    @Test
    @DisplayName("Successful user creation")
    void successfulUserCreationTest() {
        String requestBody = "{ \"name\": \"john\", \"job\": \"shop55\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("john"))
                .body("job", is("shop55"));
    }

    @Test
    @DisplayName("Checking for sending an unknown request")
    void unknownRequestTest() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("unknown/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
