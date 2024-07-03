package ru.qa.scooter.praktikum.services;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qa.scooter.praktikum.services.api.CourierApi;
import ru.qa.scooter.praktikum.services.pojo.Courier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierTest {
    private CourierApi courierApi;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        courier = new Courier("Granger", "1234", "Hermione");
    }

    @Test
    @DisplayName("Создание курьера")
    public void checkCreateCourier() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void checkCreateTwoCouriers() {
        Courier secondCourier = new Courier("Granger", "1234", "Hermione");

        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_CREATED);

        courierApi.create(secondCourier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание двух курьеров с повторяющимся логином")
    public void checkCreateCourierRepeatedLogin() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_CREATED);

        Courier secondCourier = new Courier(courier.getLogin(), "1234", "Hermione");

        courierApi.create(secondCourier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    public void checkCreateCourierWithoutFirstName() {
        Courier courierWithoutFirstName = new Courier("Granger", "1234");

        courierApi.create(courierWithoutFirstName)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @After
    public void clearData() {
        courierId = courierApi.login(courier)
                .extract().path("id");

        courierApi.delete(courierId);
    }
}
