import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class CreationOfCouriersTests {

    //Генерация данных курьеров
    private final String LOGIN = RandomStringUtils.randomAlphabetic(10);
    private final String PASSWORD = RandomStringUtils.randomAlphabetic(10);
    private final String FIRST_NAME = RandomStringUtils.randomAlphabetic(10);

    //Объекты для тестов
    CourierApiPattern courierApi;
    CourierDataPattern courier = new CourierDataPattern(LOGIN, PASSWORD, FIRST_NAME);
    CourierDataPattern courierWithoutLogin = new CourierDataPattern("", PASSWORD, FIRST_NAME);
    CourierDataPattern courierWithoutPassword = new CourierDataPattern(LOGIN, "", FIRST_NAME);
    CourierDataPattern courierForRemove = new CourierDataPattern(LOGIN, PASSWORD);

    @Before
    public void setUp() {
        courierApi = new CourierApiPattern();
    }

    //В конце каждого теста удаляем созданного курьера
    @After
    public void after() {
        courierApi.delete(courierForRemove);
    }


    //Проверка работы успешного создания курьера
    @Test
    @DisplayName("Check the status code and the server response after successful creation of the courier")
    public void successfulCreationOfCourier() {
        ValidatableResponse response = courierApi.create(courier);
        response.statusCode(201).and().assertThat().body("ok", is(true));
    }

    //Создаем двух одинаковых курьеров и проверям, что при попытке создания второго падает ошибка с текстом
    @Test
    @DisplayName("Check the status code and the server response after twice creation of the same courier")
    public void cantCreateTheSameCourier() {
        courierApi.create(courier);
        ValidatableResponse response = courierApi.create(courier);
        response.statusCode(409).and().assertThat().body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    //Проверка, что нельзя создать курьера без пароля
    @Test
    @DisplayName("Check the status code and the server response after creation of the courier without password")
    public void cantCreateTheCourierWithoutPassword() {
        ValidatableResponse response = courierApi.create(courierWithoutPassword);
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для создания учетной записи"));
    }

    //Проверка, что нельзя создать курьера без логина
    @Test
    @DisplayName("Check the status code and the server response after creation of the courier without login")
    public void cantCreateTheCourierWithoutLogin() {
        ValidatableResponse response = courierApi.create(courierWithoutLogin);
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для создания учетной записи"));
    }

}
