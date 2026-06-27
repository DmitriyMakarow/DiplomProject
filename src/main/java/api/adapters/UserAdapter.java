package api.adapters;

import api.models.InvalidUserRequest;
import api.models.UserRequest;
import api.models.UserResponse;
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
}
