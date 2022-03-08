import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetAllOrdersTest {

    OrderApiPattern orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApiPattern();
    }

    //Проверяем, что список заказов содержит данные
    @Test
    @DisplayName("Check the status code and the server response after getting of all orders")
    public void successfulCreationOfOrder() {
        ValidatableResponse response = orderApi.get();
        response.statusCode(200).and().assertThat().body("orders", notNullValue());
    }
}
