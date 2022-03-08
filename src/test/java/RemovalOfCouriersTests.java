import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.is;

public class RemovalOfCouriersTests {

    //Данные для создания курьера
    private final String LOGIN = RandomStringUtils.randomAlphabetic(10);
    private final String PASSWORD = RandomStringUtils.randomAlphabetic(10);
    private final String FIRST_NAME = RandomStringUtils.randomAlphabetic(10);

    //Объекты для данных
    CourierApiPattern courierApi;
    CourierDataPattern courierForCreation = new CourierDataPattern(LOGIN, PASSWORD, FIRST_NAME);
    CourierDataPattern courier = new CourierDataPattern(LOGIN, PASSWORD);


    @Before
    public void setUp() {
        courierApi = new CourierApiPattern();
    }

    //Успешное удаление созданного курьера
    @Test
    @DisplayName("Check the status code and the server response after deletion of the new courier")
    public void successfulRemovalOfCourier() {
        courierApi.create(courierForCreation);
        ValidatableResponse response = courierApi.delete(courier);
        response.statusCode(200).and().assertThat().body("ok", is(true));
    }

    //Проверка, что при удалении несуществующего курьера возникает ошибка и соответсвующий текст
    @Test
    @DisplayName("Check the status code and the server response after deletion of the defunct courier")
    public void RemovalOfCourierWithoutPartOfData() {
        ValidatableResponse response = courierApi.delete(courier);
        response.statusCode(404).and().assertThat().body("message", is("Курьера с таким id нет."));
    }

    //Вызов удаления курьера без id. Ответ сервера и текст: несоответсвуют документации (баг)
    @Test
    @DisplayName("Check the status code and the server response after deletion of the courier without an id")
    public void RemovalOfCourierWithoutId() {
        ValidatableResponse response = courierApi.deleteWithoutId();
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для удаления курьера"));
    }

}
