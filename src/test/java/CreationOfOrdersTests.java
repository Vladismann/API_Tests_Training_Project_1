import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreationOfOrdersTests {

    public CreationOfOrdersTests(CreationOfOrderDataPattern order){
        this.order = order;
    }

    //Параметры для заказа с разными вариациями цвета
    @Parameterized.Parameters
    public static Object[] getOrderData()
    {
        return new Object[][]{
        { new CreationOfOrderDataPattern("test", "test", "test", "1", "89869273871", 1, "2020-06-06", "test") },
        { new CreationOfOrderDataPattern("test", "test", "test", "1", "89869273871", 1, "2020-06-06", "test", new String[]{"BLACK"}) },
        { new CreationOfOrderDataPattern("test", "test", "test", "1", "89869273871", 1, "2020-06-06", "test", new String[]{"BLACK", "GREY"}) }
            };
        }

    OrderApiPattern orderApi;
    CreationOfOrderDataPattern order;

    @Before
    public void setUp() {
        orderApi = new OrderApiPattern();
    }

    //Проверяем, что заказ создается с разными вариациями выбора цвета и без
    @Test
    @DisplayName("Check the status code and the server response after creation of the order")
    public void successfulCreationOfOrder() {
        ValidatableResponse response = orderApi.create(order);
        response.statusCode(201).and().assertThat().body("track", notNullValue());
    }
}
