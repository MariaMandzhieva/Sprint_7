package ru.qa.scooter.praktikum.services;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.qa.scooter.praktikum.services.api.OrderApi;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrderListTest {
    private OrderApi orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Check get orders list")
    public void checkGetOrders() {
        orderApi.getOrdersList()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());

        int actualOrdersListSize = orderApi.getOrdersList()
                .extract().jsonPath()
                .getList("orders").size();

        assertEquals(30, actualOrdersListSize);
    }
}
