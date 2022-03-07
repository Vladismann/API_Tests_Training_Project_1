import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierApiPattern extends ScooterRestPattern {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Create a courier")
    public ValidatableResponse create (CourierDataPattern courier) {

        return given()
                .spec(getBaseSpec())
                .body(courier)
                .post(COURIER_PATH)
                .then();
    }

    @Step("Login of the courier")
    public ValidatableResponse login (CourierDataPattern courier) {

        return given()
                .spec(getBaseSpec())
                .body(courier)
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Remove the courier")
    public ValidatableResponse delete (CourierDataPattern courier) {

        Response response = given()
                .spec(getBaseSpec())
                .body(courier)
                .post(COURIER_PATH + "login");

        int newCourierId = Integer.parseInt(response.body().asString().replaceAll("\\D+",""));

        return given()
                .spec(getBaseSpec())
                .and()
                .delete(COURIER_PATH + newCourierId)
                .then();
    }

    @Step("Remove the courier without id")
    public ValidatableResponse deleteWithoutId () {

        return given()
                .spec(getBaseSpec())
                .and()
                .delete(COURIER_PATH)
                .then();
    }

}
