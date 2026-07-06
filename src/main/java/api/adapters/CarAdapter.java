package api.adapters;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import ui.dto.cars.CarTestDataFactory;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static ui.pages.base.BasePage.faker;

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

    public List<Integer> createCarsAndGetIds(int count, int status) {
        List<Integer> createdIds = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            CarRequest request = CarTestDataFactory.validCarTestDataAPI();

            var resp = given()
                    .spec(getSpec())
                    .body(gson.toJson(request))
                    .log().all()
                    .when()
                    .post("/car")
                    .then()
                    .log().all()
                    .statusCode(status);

            if (status == 201) {
                CarResponse car = resp.extract().as(CarResponse.class);
                createdIds.add(car.getId());
            }
        }

        return createdIds;
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

    public void deleteApiCars(List<Integer> ids) {
        for (Integer id : ids) {
            deleteApiCar(id);
        }
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

    public void buyCars(Integer userId, List<Integer> carIds, int limit, int status, Class<?> clazz) {
        if (carIds == null || carIds.isEmpty()) {
            throw new IllegalArgumentException("Список ID машин не может быть null или пустым");
        }

        // Определяем, сколько машин будем покупать
        int carsToBuy = Math.min(limit, carIds.size());

        // Берем только первые 'limit' машин
        List<Integer> carsToPurchase = carIds.subList(0, carsToBuy);

        for (Integer carId : carsToPurchase) {
            buyCar(userId, carId, status, clazz);
        }
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

    public <T> T sellCars(Integer userId, List<Integer> carIds, int status, Class<T> clazz) {
        T result = null;

        for (Integer carId : carIds) {
            result = sellCar(userId, carId, status, clazz);
        }

        return result;
    }
}
