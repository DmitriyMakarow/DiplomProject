package api.adapters;

import api.models.houses.HouseRequest;
import api.models.houses.HouseResponse;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.util.List;

import static io.restassured.RestAssured.given;

public class HouseAdapter extends BaseAdapter {

    public HouseResponse getHouse(Integer id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .get("/house/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/houseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public List<HouseResponse> getHouses() {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .get("/houses")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<HouseResponse>>() {});
    }

    public HouseResponse createApiHouse(HouseRequest houseRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(houseRequest))
                .log().all()
                .when()
                .post("/house")
                .then()
                .log().all()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/houseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public HouseResponse putApiHouse(Integer id, HouseRequest houseRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(houseRequest))
                .pathParam("id", id)
                .log().all()
                .when()
                .put("/house/{id}")
                .then()
                .log().all()
                .statusCode(202)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/houseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public void deleteApiHouse(Integer id) {
        given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .delete("/house/{id}")
                .then()
                .log().all()
                .spec(code204);
    }

    public HouseResponse settleUser(Integer houseId, Integer userId) {
        return given()
                .spec(getSpec())
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .post("/house/{houseId}/settle/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(HouseResponse.class);
    }

    public HouseResponse evictUser(Integer houseId, Integer userId) {
        return given()
                .spec(getSpec())
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .post("/house/{houseId}/evict/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(HouseResponse.class);
    }
}