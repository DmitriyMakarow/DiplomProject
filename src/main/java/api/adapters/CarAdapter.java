package api.adapters;

import api.models.CarRequest;
import api.models.CarResponse;
import api.models.UserResponse;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

public class CarAdapter extends BaseAdapter {

    public CarResponse getCar(Integer id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .get("/car/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"))
                .extract()
                .as(CarResponse.class);
    }

    public CarResponse getCars() {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .get("/cars")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/listCarsSchema.json"))
                .extract()
                .as(CarResponse.class);
    }

    public CarResponse createApiCar(CarRequest carRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(carRequest))
                .log().all()
                .when()
                .post("/car")
                .then()
                .log().all()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"))
                .extract()
                .as(CarResponse.class);
    }

    public CarResponse putApiCar(Integer id, CarRequest carRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(carRequest))
                .pathParam("id", id)
                .log().all()
                .when()
                .put("/car/{id}")
                .then()
                .log().all()
                .statusCode(202)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"))
                .extract()
                .as(CarResponse.class);
    }

    public void deleteApiCar(Integer id) {
        given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .delete("/car/{id}")
                .then()
                .log().all()
                .spec(code204);
    }

    public UserResponse successBuyApiCar(Integer userId, Integer carId) {
        return given()
                .spec(getSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public UserResponse buyNotEnoughMoneyApiCar(Integer userId, Integer carId) {
        return given()
                .spec(getSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .log().all()
                .statusCode(406)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public UserResponse successSellApiCar(Integer userId, Integer carId) {
        return given()
                .spec(getSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public UserResponse sellNoHaveApiCar(Integer userId, Integer carId) {
        return given()
                .spec(getSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .log().all()
                .statusCode(406)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }
}
