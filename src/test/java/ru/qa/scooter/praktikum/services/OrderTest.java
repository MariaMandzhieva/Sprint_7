package ru.qa.scooter.praktikum.services;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qa.scooter.praktikum.services.api.OrderApi;
import ru.qa.scooter.praktikum.services.pojo.Order;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    private OrderApi orderApi;
    private Order order;
    private ValidatableResponse response;

    public OrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 3, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 3, "2020-06-06", "Saske, come back to Konoha", new String[]{"GREY"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 3, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 3, "2020-06-06", "Saske, come back to Konoha", new String[]{})},};
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Check create orders")
    public void checkCreateOrder() {
        response = orderApi.create(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }
}
