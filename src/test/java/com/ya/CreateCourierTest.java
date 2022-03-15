package com.ya;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    CourierClient courierClient;
    int courierId;
    String courierLogin = RandomStringUtils.randomAlphabetic(10);
    String courierPassword = RandomStringUtils.randomAlphabetic(10);
    String courierFirstName = RandomStringUtils.randomAlphabetic(10);

   @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier can be created")
    public void courierCanBeCreated(){
        ValidatableResponse createResponse = courierClient.create(new Courier(courierLogin, courierPassword, courierFirstName));
        int statusCode = createResponse.extract().statusCode();
        boolean ok = createResponse.extract().path("ok");
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courierLogin, courierPassword));
        courierId = loginResponse.extract().path("id");

        assertThat("Courier has created", statusCode, equalTo(201));
        assertThat("State if courier has created", ok, equalTo(true));
    }

    @Test
    @DisplayName("Courier must be unique")
    public void courierMustBeUnique(){
        ValidatableResponse createResponse = courierClient.create(new Courier("courier.login", "courier.password","courier.firstName"));
        ValidatableResponse createResponse1 = courierClient.create(new Courier("courier.login", "courier.password","courier.firstName"));

        int statusCode = createResponse1.extract().statusCode();
        String message = createResponse1.extract().path("message");

        assertThat("Courier hasn't created", statusCode, equalTo(409));
        assertThat("Message if cannot create", message, equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Courier cannot created without password")
    public void courierCannotCreatedWithoutPassword(){
        ValidatableResponse createResponse = courierClient.create(new Courier(courierLogin, "", courierFirstName));
        int statusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");

        assertThat("Courier hasn't created", statusCode, equalTo(400));
        assertThat("Message if cannot create", message, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier cannot created without login")
    public void courierCannotCreatedWithoutLogin(){
        ValidatableResponse createResponse = courierClient.create(new Courier("", courierPassword, courierFirstName));
        int statusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");

        assertThat("Courier hasn't created", statusCode, equalTo(400));
        assertThat("Message if cannot create", message, equalTo("Недостаточно данных для создания учетной записи"));
    }
}
