package api.adapters;

import api.models.UserResponse;

import static api.adapters.BaseAdapter.getSpec;
import static io.restassured.RestAssured.given;

public class UserAdapter {

    public static UserResponse checkAddMoneyUser(int amount){
               UserResponse userAfter =given()
                .spec(getSpec())
                .baseUri("http://82.142.167.37:4879")
                .basePath("/user/6936/money")
                .log().all()// встроенное логирование
                .when()
                .post("/{amount}")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);
        return userAfter;
    }

    public static UserResponse getUserBalance() {
        return given()
                .spec(getSpec())
                .baseUri("http://82.142.167.37:4879")
                .basePath("/user/6936")
                .log().all()
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(UserResponse.class);
    }
}

