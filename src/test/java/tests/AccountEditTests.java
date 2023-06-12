package tests;

import org.junit.jupiter.api.Test;

import static helpers.api.AuthApi.authCookieKey;
import static io.restassured.RestAssured.given;

public class AccountEditTests extends TestBase {

    @Test
    void editAddressValuesTest() {

        String valueId = "3112806",
                valueFirstName = "John",
                valueLastName = "Smith",
                valueEmail = "js121324123@gmail.com",
                valueCompany = "Micro",
                valueCountryId = "11",
                valueStateProvinceId = "12",
                valueCity = "NY",
                valueAddress1 = "53d",
                valueAddress2 = "3d",
                valueZipPostalCode = "12312305",
                valuePhoneNumber = "1434534534537",
                valueFaxNumber = "1569876687968796";

        String authCookieValue = authApi.getAuthCookie(login, password);

        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie(authCookieKey, authCookieValue)
                .formParam("Address.Id", valueId)
                .formParam("Address.FirstName", valueFirstName)
                .formParam("Address.LastName", valueLastName)
                .formParam("Address.Email", valueEmail)
                .formParam("Address.Company", valueCompany)
                .formParam("Address.CountryId", valueCountryId)
                .formParam("Address.StateProvinceId", valueStateProvinceId)
                .formParam("Address.City", valueCity)
                .formParam("Address.Address1", valueAddress1)
                .formParam("Address.Address2", valueAddress2)
                .formParam("Address.ZipPostalCode", valueZipPostalCode)
                .formParam("Address.PhoneNumber", valuePhoneNumber)
                .formParam("Address.FaxNumber", valueFaxNumber)
                .when()
                .post("/customer/addressedit/3112806")
                .then()
                .log().all()
                .statusCode(302);
    }

}
