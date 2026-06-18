package api.adapters;

import api.models.LoginRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;

public class BaseAdapter {

    private static final String user = System.getProperty("user", utils.PropertyReader.getProperty("user"));
    private static final String password = System.getProperty("password", PropertyReader.getProperty("password"));

    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    public static RequestSpecification getSpec() {
        LoginRequest loginRequest = LoginRequest.builder()
                .password(password)
                .userName(user)
                .build();

        String token = given()
                .contentType(ContentType.JSON)
                .baseUri("http://82.142.167.37:4879")
                .body(BaseAdapter.gson.toJson(loginRequest))
                .log().all()
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(202)
                .extract()
                .path("access_token");

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://82.142.167.37:4879")
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static ResponseSpecification code200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification code204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();
}
