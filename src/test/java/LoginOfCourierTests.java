import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class LoginOfCourierTests {

    //Тестовый курьер для проверки логина (всегда действующий, запрещен к удалению)
    private final String LOGIN = "autotest";
    private final String PASSWORD = "1234";
    //Данные для негативного тестирования
    private final String INCORRECT_PASSWORD = "12345";
    private final String DEFUNCT_LOGIN = "null";

    //Объекты для тестов
    CourierApiPattern courierApi;
    CourierDataPattern courier = new CourierDataPattern(LOGIN, PASSWORD);
    CourierDataPattern courierWithIncorrectPassword = new CourierDataPattern(LOGIN, INCORRECT_PASSWORD);
    CourierDataPattern courierWithDefunctLogin = new CourierDataPattern(DEFUNCT_LOGIN, PASSWORD);
    CourierDataPattern courierWithoutPassword = new CourierDataPattern(LOGIN, "");

    @Before
    public void setUp() {
        courierApi = new CourierApiPattern();
    }

    //Проверяем успешный логин существующего курьера
    @Test
    @DisplayName("Check the status code and the server response after successful login of the courier")
    public void successfulLoginOfCourier() {
        ValidatableResponse response = courierApi.login(courier);
        response.statusCode(200).and().assertThat().body("id", notNullValue());
    }

    //Проверяем код и текст ответа при авторизации с неверным паролем
    @Test
    @DisplayName("Check the status code and the server response after login of the courier with the incorrect password")
    public void LoginOfCourierWithIncorrectPassword() {
        ValidatableResponse response = courierApi.login(courierWithIncorrectPassword);
        response.statusCode(404).and().assertThat().body("message", is("Учетная запись не найдена"));
    }

    //Проверяем код и текст ответа при авторизации с неуществующим логином
    @Test
    @DisplayName("Check the status code and the server response after login of the courier with the defunct login")
    public void entranceOfCourierWithDefunctLogin() {
        ValidatableResponse response = courierApi.login(courierWithDefunctLogin);
        response.statusCode(404).and().assertThat().body("message", is("Учетная запись не найдена"));
    }

    //Проверяем код и текст ответа при авторизации с неуществующим логином
    @Test
    @DisplayName("Check the status code and the server response after login of the courier without the password")
    public void entranceOfCourierWithoutPassword() {
        ValidatableResponse response = courierApi.login(courierWithoutPassword);
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для входа"));
    }

}
