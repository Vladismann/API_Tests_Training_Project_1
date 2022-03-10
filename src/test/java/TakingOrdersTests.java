import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class TakingOrdersTests {

    //ID постоянного тестового курьера логин: "autotest" | пароль: "1234"
    private final String COURIER_ID = "34010";

    CreationOfOrderDataPattern order = new CreationOfOrderDataPattern();
    OrderApiPattern orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApiPattern();
        orderApi.create(order).extract().path("track");
    }

    //Проверка работы успешного принятия заказа
    @Test
    @DisplayName("Check the status code and the server response after taking the new order by order id and courier id")
    public void successfulAcceptanceOfNewOrder() {
        //Получаем id нового заказа, чтобы применить его в запросе принятия заказа
        int trackNumber = orderApi.create(order).extract().path("track");
        String orderId = orderApi.getByTrackNumber(trackNumber).extract().path("order.id").toString();

        ValidatableResponse response = orderApi.orderAcceptance(orderId, COURIER_ID);
        response.statusCode(200).and().assertThat().body("ok", is(true));
    }

    @Test
    @DisplayName("Check the status code and the server response after taking the order which already taken")
    public void acceptanceOfOrderWhichAlreadyTaken() {
        //Получаем id нового заказа, чтобы применить его в запросе принятия заказа, принимаем заказ два раза и на второй раз проверяем
        int trackNumber = orderApi.create(order).extract().path("track");
        String orderId = orderApi.getByTrackNumber(trackNumber).extract().path("order.id").toString();
        orderApi.orderAcceptance(orderId, COURIER_ID);

        ValidatableResponse response = orderApi.orderAcceptance(orderId, COURIER_ID);
        response.statusCode(409).and().assertThat().body("message", is("Этот заказ уже в работе"));
    }

    //Проверка, что нельзя принять заказ без id курьера
    @Test
    @DisplayName("Check the status code and the server response after taking the new order by order id and without courier id")
    public void acceptanceOfNewOrderWithoutCourierId() {
        //Получаем id нового заказа, чтобы применить его в запросе принятия заказа
        int trackNumber = orderApi.create(order).extract().path("track");
        String orderId = orderApi.getByTrackNumber(trackNumber).extract().path("order.id").toString();

        ValidatableResponse response = orderApi.orderAcceptance(orderId, "");
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для поиска"));
    }

    //Проверка, что нельзя принять заказ без с неверным id курьера
    @Test
    @DisplayName("Check the status code and the server response after taking the new order by order id and wrong courier id")
    public void acceptanceOfNewOrderWithWrongCourierId() {
        //Получаем id нового заказа, чтобы применить его в запросе принятия заказа
        int trackNumber = orderApi.create(order).extract().path("track");
        String orderId = orderApi.getByTrackNumber(trackNumber).extract().path("order.id").toString();

        ValidatableResponse response = orderApi.orderAcceptance(orderId, "12345");
        response.statusCode(404).and().assertThat().body("message", is("Курьера с таким id не существует"));
    }

    //Проверка, что нельзя принять заказ с неверным id заказа
    @Test
    @DisplayName("Check the status code and the server response after taking the new order by wrong order id and courier id")
    public void acceptanceOfNewOrderWithWrongOrderId() {
        ValidatableResponse response = orderApi.orderAcceptance("666666", COURIER_ID);
        response.statusCode(404).and().assertThat().body("message", is("Заказа с таким id не существует"));
    }

    //Проверка, что нельзя принять заказ с пустым id заказа
    @Test
    @DisplayName("Check the status code and the server response after taking the new order by empty order id and courier id")
    public void acceptanceOfNewOrderWithoutOrderId() {
        ValidatableResponse response = orderApi.orderAcceptance("", COURIER_ID);
        response.statusCode(400).and().assertThat().body("message", is("Недостаточно данных для поиска"));
    }

}
