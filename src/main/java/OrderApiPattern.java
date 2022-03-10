import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderApiPattern extends ScooterRestPattern {

    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Create an order")
    public ValidatableResponse create(CreationOfOrderDataPattern order) {
        return given()
                .log().all()
                .spec(getBaseSpec())
                .body(order)
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get all orders")
    public ValidatableResponse get() {
        return given()
                .log().all()
                .spec(getBaseSpec())
                .get(ORDER_PATH)
                .then();
    }

    @Step("Accept the order by the courier")
    public ValidatableResponse orderAcceptance(String orderId, String courierId) {
        if (orderId == null || orderId.equals("")) {
            return given()
                    .log().all().spec(getBaseSpec())
                    .put(ORDER_PATH + "accept/" + "courierId =" + courierId)
                    .then();
        } else {
            return given()
                    .log().all()
                    .spec(getBaseSpec())
                    .queryParam("courierId", courierId).put(ORDER_PATH + "accept/" + orderId)
                    .then();
        }
    }

    @Step("Get an order data by the track number")
    public ValidatableResponse getByTrackNumber(int trackNumber) {
        return given()
                .log().all()
                .spec(getBaseSpec())
                .queryParam("t", trackNumber)
                .get(ORDER_PATH + "track")
                .then();
    }

    @Step("Get the order data without a track number")
    public ValidatableResponse getWithoutTrackNumber() {
        return given()
                .log().all()
                .spec(getBaseSpec())
                .queryParam("t")
                .get(ORDER_PATH + "track")
                .then();
    }

}
