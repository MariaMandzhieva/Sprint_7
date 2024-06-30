package ru.qa.scooter.praktikum.services.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.qa.scooter.praktikum.services.pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderApi extends ScooterRestClient{
    private static final String ORDERS_URI = BASE_URI + "orders/";

    @Step("Получить список заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getReqSpec())
                .get(ORDERS_URI)
                .then();
    }

    @Step("Создать заказ")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getReqSpec())
                .body(order)
                .post(ORDERS_URI)
                .then();
    }
}
