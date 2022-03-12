package com.ya;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    CourierClient courierClient;
    Courier courier;
//   List<String> couriers = Arrays.asList(courier.login, courier.password,courier.firstName);
    int courierId;

    /*Тут аналогично классу LoginCourierTest все тесты падают. Думаю, проблема та же: некорректно создаю курьера и
    передаю значения логина и пароля. Помогите разобраться, пожалуйста.
     */

   @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = new Courier("unikalniynyc", "1234", "test");

    }

    @After
    public void tearDown(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierId = loginResponse.extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    public void courierCanBeCreated(){
        ValidatableResponse createResponse = courierClient.create(new Courier(courier.login, courier.password, courier.firstName));
        int statusCode = createResponse.extract().statusCode();
        boolean ok = createResponse.extract().path("ok");

        assertThat("Courier created", statusCode, equalTo(201));
        assertThat(ok, equalTo(true));
    }

    @Test
    public void courierMustBeUnique(){
        ValidatableResponse createResponse = courierClient.create(new Courier("courier.login", "courier.password","courier.firstName"));
        ValidatableResponse createResponse1 = courierClient.create(new Courier("courier.login", "courier.password","courier.firstName"));

        int statusCode = createResponse1.extract().statusCode();
        String message = createResponse1.extract().path("message");

        assertThat("Courier cannot login", statusCode, equalTo(409));
        assertThat("Courier is incorrect", message, equalTo("Этот логин уже используется"));
    }

    @Test
    public void courierCannotCreatedWithoutPassword(){
        ValidatableResponse createResponse = courierClient.create(new Courier(courier.login, "", courier.firstName));
        int statusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void courierCannotCreatedWithoutLogin(){
        ValidatableResponse createResponse = courierClient.create(new Courier("", courier.password, courier.firstName));
        int statusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для создания учетной записи"));
    }
}
