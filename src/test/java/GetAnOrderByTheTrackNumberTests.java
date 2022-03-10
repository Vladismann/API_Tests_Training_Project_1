import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetAnOrderByTheTrackNumberTests {

    //Постоянный тестовый заказ
    final int ORDER_TRACK_NUMBER = 973934;

    OrderApiPattern orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApiPattern();
    }

    //Проверяем, что по трэк номеру заказа можно получить данные заказа
    @Test
    @DisplayName("Check the status code and the server response after getting the order by the track number")
    public void getOrderDataByTrackNumber() {
        ValidatableResponse response = orderApi.getByTrackNumber(ORDER_TRACK_NUMBER);
        response.statusCode(200).and().assertThat().body("order", notNullValue());
    }

    //Проверяем, что без трэк номера заказа сервер выдает ошибку и соответсвующий текст
    @Test
    @DisplayName("Check the status code and the server response after getting the order without the track number")
    public void getOrderDataWithoutTrackNumber() {
        ValidatableResponse response = orderApi.getWithoutTrackNumber();
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для поиска"));
    }

    //Проверяем, что неверного трэк номера заказа сервер выдает ошибку и соответсвующий текст
    @Test
    @DisplayName("Check the status code and the server response after getting the order with the wrong track number")
    public void getOrderDataByWrongTrackNumber() {
        ValidatableResponse response = orderApi.getByTrackNumber(0);
        response.statusCode(404).and().assertThat().body("message", is("Заказ не найден"));
    }
}
