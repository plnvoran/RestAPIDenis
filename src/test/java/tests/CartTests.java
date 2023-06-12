package tests;

import models.AddingItemResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static helpers.api.AuthApi.authCookieKey;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends TestBase {
    String authCookieValue;
    int numberOfItemsInCart;
    int quantityForAdd = 2;
    String data = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addtocart_72.EnteredQuantity=" + quantityForAdd;

    @Test
    void addToCartAsAuthorizedTest() {
        step("Get auth cookie by api and set it to browser", () ->
                authCookieValue = authApi.getAuthCookie(login, password));

        step("Get the number of items in the cart", () -> {
            String page = given()
                    .cookie(authCookieKey, authCookieValue)
                    .when()
                    .get()
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

            Document document = Jsoup.parse(page);
            String numbersOfItemsString = document.select(".cart-qty").text();
            numberOfItemsInCart = Integer.parseInt(numbersOfItemsString.substring(1, numbersOfItemsString.length() - 1));

            System.out.println("The number of items in the cart:");
            System.out.println(numberOfItemsInCart);
        });

        AddingItemResponse response = step("Adding items to the cart", () -> given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieKey, authCookieValue)
                .body(data)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(AddingItemResponse.class));

        step("Checking the correct number of items in the cart", () -> {
            assertThat(response.getSuccess()).isEqualTo("true");
            assertThat(response.getMessage()).isEqualTo("The product has been added to your <a href=\"/cart\">shopping cart</a>");
            assertThat(response.getUpdatetopcartsectionhtml()).isEqualTo("(" + (numberOfItemsInCart + quantityForAdd) + ")");
        });

    }
}


