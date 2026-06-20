package api.adapters;

import api.models.HouseRequest;
import api.models.HouseResponse;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

public class HouseAdapter extends BaseAdapter {

    private static final String AUTH_URL = "http://82.142.167.37:4879";
    private static final String API_URL = "http://82.142.167.37:4879";

    private String getAuthToken() {
        return given()
                .baseUri(AUTH_URL)
                .contentType("application/json")
                .body("{\"username\": \"user@pflb.ru\", \"password\": \"user\"}")
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(202)
                .extract()
                .path("access_token");
    }

    private io.restassured.specification.RequestSpecification getHouseSpec() {
        String token = getAuthToken();
        return given()
                .baseUri(API_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json");
    }

    public HouseResponse getHouse(Integer id) {
        return getHouseSpec()
                .pathParam("id", id)
                .log().all()
                .when()
                .get("/house/{id}")  // ← БЕЗ /api
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/houseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public HouseResponse getHouses() {
        return given()
                .baseUri(API_URL)
                .header("Authorization", "Bearer " + getAuthToken())
                .log().all()
                .when()
                .get("/houses")  // ← БЕЗ /api
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(HouseResponse.class);
    }

    public HouseResponse createApiHouse(HouseRequest houseRequest) {
        return getHouseSpec()
                .body(gson.toJson(houseRequest))
                .log().all()
                .when()
                .post("/house")  // ← БЕЗ /api
                .then()
                .log().all()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/houseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public HouseResponse putApiHouse(Integer id, HouseRequest houseRequest) {
        return getHouseSpec()
                .body(gson.toJson(houseRequest))
                .pathParam("id", id)
                .log().all()
                .when()
                .put("/house/{id}")  // ← БЕЗ /api
                .then()
                .log().all()
                .statusCode(202)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/houseSchema.json"))
                .extract()
                .as(HouseResponse.class);
    }

    public void deleteApiHouse(Integer id) {
        given()
                .baseUri(API_URL)
                .header("Authorization", "Bearer " + getAuthToken())
                .pathParam("id", id)
                .log().all()
                .when()
                .delete("/house/{id}")  // ← БЕЗ /api
                .then()
                .log().all()
                .statusCode(204);
    }
}