package ru.qa.scooter.praktikum.services;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qa.scooter.praktikum.services.api.CourierApi;
import ru.qa.scooter.praktikum.services.pojo.Courier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierApi courierApi;
    private Courier courier;
    private Integer courierId;

    @Before
    public void createCourier() {
        courierApi = new CourierApi();
        courier = new Courier("Granger", "1234", "Hermione");
        courierApi.create(courier);
    }

    @Test
    @DisplayName("Проверка авторизации курьера")
    public void checkLoginCourier() {
        courierApi.login(courier)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неправильными логином")
    public void checkLoginCourierWithIncorrectLogin() {
       courierApi.login(new Courier("Grngr", courier.getPassword()))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неправильными паролем")
    public void checkLoginCourierWithIncorrectPassword() {
        courierApi.login(new Courier(courier.getLogin(), "1235"))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    public void checkLoginNotExistedCourier() {
        courierApi.login(new Courier("Ron","5678"))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void checkLoginCourierWithoutLogin() {
        courierApi.login(new Courier(null,"1234"))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body( "message", equalTo("Недостаточно данных для входа"));
    }
    /*@Test
    @DisplayName("Авторизация без пароля")
    public void checkLoginCourierWithoutPassword() {
        courierApi.login(new Courier("Ron",null))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body( "message", equalTo("Недостаточно данных для входа"));
    }
    !!!Service unavailable!!!
     */

    @After
    public void clearData() {
        courierId = courierApi.login(courier)
                .extract().path("id");

        courierApi.delete(courierId);
    }
}
