package ru.qa.scooter.praktikum.services;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qa.scooter.praktikum.services.api.CourierApi;
import ru.qa.scooter.praktikum.services.pojo.Courier;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class InvalidCourierTest {
    private CourierApi courierApi;
    private Courier courier;
    private Integer courierId;

    public InvalidCourierTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier(null, "1234", "Hermione")},
                {new Courier("Granger", null, "Hermione")},
        };
    }

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @Test
    @DisplayName("Создание курьера без логина или пароля")
    public void checkCreateCourierWithoutLoginOrPassword() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @After
    public void clear(){
        try{
            courierId = courierApi.login(courier)
                    .extract().path("id");
            courierApi.delete(courierId);
        }catch (Exception exception){
            System.out.println("Курьер не был создан");
        }
    }
}
