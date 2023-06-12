package tests;

import com.codeborne.selenide.Configuration;
import helpers.api.AuthApi;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    String login = "plnvoran@mail.ru",
            password = "pln123";
    AuthApi authApi = new AuthApi();

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demowebshop.tricentis.com";
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }
}
