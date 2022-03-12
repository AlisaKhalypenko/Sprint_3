package com.ya;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class LoginCourierTest {
    CourierClient courierClient;
    Courier courier;
    int courierId;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        /* Не удалось разобраться с тем, как передать курьеру рандомные значения логина, пароля и имени.
        Из задания проекта скоприрован код в класс ScooterRegisterCourier, но не понимаю, как его применить?
        После исполнения метода класса ScooterRegisterCourier должен получиться список значений. Как список
        передать объекту класса Courier, который принимает 3 значения типа String? Объясните, пожалуйста.*/

        courier = new Courier("unikalniynyc", "1234", "test");
        courierClient.create(courier);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    /*  в этом классе все тесты падают. Курьер создаётся, но не может залогиниться.
     Код идентичен тому, что давали на вебинаре. Почему так происходит? Чего-то не хватает методу login? Чего именно?*/


    @Test
    public void courierCanLoginWithValidCredentials(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.login, courier.password));
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertThat("Courier cannot login", statusCode, equalTo(SC_OK));
        //assertThat("Courier id is incorrect", courierId, is(not(0)));

    }

    @Test
    public void courierCannotLoginWithoutPassword(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.login, "" ));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void courierCannotLoginWithoutLogin(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.password));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat(statusCode, equalTo(400));
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void courierCannotLoginWithIncorrectLogin(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("kJSRbgkj", courier.password));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat(statusCode, equalTo(404));
        assertThat(message, equalTo("Учетная запись не найдена"));
    }

    @Test
    public void courierCannotLoginWithIncorrectPassword(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.login, "zteznz"));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat(statusCode, equalTo(404));
        assertThat(message, equalTo("Учетная запись не найдена"));
    }

    @Test
    public void courierCannotLoginIfNotCreated(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("test", "test"));
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertThat(statusCode, equalTo(404));
        assertThat(message, equalTo("Учетная запись не найдена"));
    }
}
