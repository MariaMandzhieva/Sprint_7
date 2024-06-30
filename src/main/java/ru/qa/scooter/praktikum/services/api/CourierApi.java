package ru.qa.scooter.praktikum.services.api;

import io.qameta.allure.Step;
import ru.qa.scooter.praktikum.services.pojo.Courier;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRestClient{
    private static final String COURIER_URI = BASE_URI + "courier/";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .post(COURIER_URI)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(Courier courier) {
        return given()
                .spec(getReqSpec())
                .body(courier)
                .post(COURIER_URI + "login/")
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getReqSpec())
                .when()
                .delete(COURIER_URI + id)
                .then();
    }
}
