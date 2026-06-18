package api.adapters;

import api.models.CarRequest;
import api.models.CarResponse;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

public class CarAdapter extends BaseAdapter {

    public CarResponse createCar(CarRequest carRequest) {
        return given()
                .spec(spec)
                .body(gson.toJson(carRequest))
                .log().all()
                .when()
                .post("/car")
                .then()
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"))
                .statusCode(200)
                .extract()
                .as(CarResponse.class);
    }
}
