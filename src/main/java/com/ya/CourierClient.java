package com.ya;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient{
    private static final String COURIER_PATH = "/api/v1/courier";

    public ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }

    public ValidatableResponse create (Courier courier){
        String courierLogin = courier.getLogin();
        String courierPassword = courier.getPassword();
        String courierFirstName = courier.getFirstName();

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        return given()
                .spec(getBaseSpec())
                .body(registerRequestBody)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public void delete (int courierId){
        given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH +  "/" + courierId)
                .then();
    }

    public ValidatableResponse orderCreating (Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    public OrdersList orderListGetting () {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/api/v1/orders")
                .body().as(OrdersList.class);
    }


}
