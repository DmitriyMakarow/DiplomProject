package api.adapters;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import api.models.users.UserResponse;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

public class CarAdapter extends BaseAdapter {

    public <T> T getCar(Integer id, int status, Class<T> clazz) {
        var resp = given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .get("/car/{id}")
                .then()
                .log().all()
                .statusCode(status);

        if (clazz != null) {
            resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"));
            return resp.extract().as(clazz);
        }

        return null;
    }

    public CarResponse getCars() {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .get("/cars")
                .then()
                .log().all()
                .spec(code200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/listCarsSchema.json"))
                .extract()
                .as(CarResponse.class);
    }

    public <T> T createCar(CarRequest carRequest, int status, Class<T> clazz) {
        var resp = given()
                .spec(getSpec())
                .body(gson.toJson(carRequest))
                .log().all()
                .when()
                .post("/car")
                .then()
                .log().all()
                .statusCode(status);

        if (clazz != null) {
            resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"));
            return resp.extract().as(clazz);
        }

        return null;
    }

    public <T> T putCar(Integer id, CarRequest carRequest, int status, Class<T> clazz) {
        var resp = given()
                .spec(getSpec())
                .body(gson.toJson(carRequest))
                .pathParam("id", id)
                .log().all()
                .when()
                .put("/car/{id}")
                .then()
                .log().all()
                .statusCode(status);

        if (clazz != null) {
            resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/carSchema.json"));
            return resp.extract().as(clazz);
        }

        return null;
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

    public <T> T buyCar(Integer userId, Integer carId, int status, Class<T> clazz) {
        var resp = given()
                .spec(getSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .log().all()
                .statusCode(status);

        if (clazz != null) {
            resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"));
            return resp.extract().as(clazz);
        }

        return null;
    }

    public <T> T sellCar(Integer userId, Integer carId, int status, Class<T> clazz) {
        var resp = given()
                .spec(getSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .log().all()
                .statusCode(status);

        if (clazz != null) {
            resp.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"));
            return resp.extract().as(clazz);
        }

        return null;
    }
}
