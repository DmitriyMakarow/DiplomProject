package api.adapters;

import api.models.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserAdapter extends BaseAdapter {

    public UserResponse createUser(UserRequest userRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(userRequest))
                .log().all()
                .when()
                .post("/user")
                .then()
                .log().all()
                .statusCode(201)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public UserResponse putValidApiUser(Integer id, UserRequest userRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(userRequest))
                .pathParam("id", id)
                .log().all()
                .when()
                .put("/user/{id}")
                .then()
                .log().all()
                .statusCode(202)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public Response putInvalidApiUser(Integer id, InvalidUserRequest userRequest) {
        return given()
                .spec(getSpec())
                .body(gson.toJson(userRequest))
                .pathParam("id", id)
                .log().all()
                .when()
                .put("/user/{id}")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public UserResponse getUser(Integer id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .get("/user/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public double getUserBalance(Integer userId) {
        UserResponse userInfo = this.getUser(userId);
        return userInfo.getMoney();
    }

    public List<UserResponse> getUsers() {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .get("/users")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", UserResponse.class);
    }

    public void deleteUser(Integer id) {
        given()
                .spec(getSpec())
                .pathParam("id", id)
                .log().all()
                .when()
                .delete("/user/{id}")
                .then()
                .log().all()
                .spec(code204);
    }

    public UserResponse postUserMoney(Integer id, double amount) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .pathParam("amount", amount)
                .log().all()
                .when()
                .post("/user/{id}/money/{amount}")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userSchema.json"))
                .extract()
                .as(UserResponse.class);
    }

    public Response negativePostUserMoney(Integer id, double amount) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .pathParam("amount", amount)
                .log().all()
                .when()
                .post("/user/{id}/money/{amount}")
                .then()
                .log().all()
                .extract()
                .response();
    }
  
    public <T> List<T> getCarsByUser(Class<T> CarResponse, Integer id) {
        return given()
                .spec(getSpec())
                .pathParam("userId", id)
                .log().all()
                .when()
                .get("/user/{userId}/cars")
                .then()
                .log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/listCarsSchema.json"))
                .extract()
                .jsonPath().getList("$", CarResponse);
    }

    public void getEmptyListCarsByUser(Integer id) {
         given()
                .spec(getSpec())
                .pathParam("userId", id)
                .log().all()
                .when()
                .get("/user/{userId}/cars")
                .then()
                .log().all()
                .statusCode(204);
    }
}

