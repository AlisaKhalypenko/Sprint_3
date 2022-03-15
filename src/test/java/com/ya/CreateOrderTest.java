package com.ya;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    CourierClient courierClient;
    Order order;
    int track;

    private final String color;
    private final String expected;

    public CreateOrderTest(String color, String expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"BLACK", "BLACK"},
                {"GREY", "GREY"},
                {"BLACK, GREY", "BLACK, GREY"},
                {"", ""},
        });
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha",
                List.of(color));
    }

    @Test
    @DisplayName("Order was created with colors")
    public void orderWasCreatedWithColors() {
        ValidatableResponse orderCreated = courierClient.orderCreating(order);
        int statusCode = orderCreated.extract().statusCode();
        track = orderCreated.extract().path("track");

        assertThat("Order was created", statusCode, equalTo(201));
        assertThat("Order track is correct", track, is(not(0)));
    }

    @Test
    @DisplayName("Orders list has received")
    public void ordersListReceived() {
        OrdersList ordersList = courierClient.orderListGetting();
        assertThat("Order list is correct", ordersList, is(not(0)));
    }
}
