package api.adapters;

import api.models.LoanResponse;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

public class LoanAdapter extends BaseAdapter {

    public static LoanResponse RequestALoan(String id, String amount) {
        return given()
                .spec(getSpec())
                .config(config)
                .log().all()
                .when()
                .post("/user/%s/loan/%s".formatted(id, amount))
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/loanSchema.json"))
                .log().all()
                .statusCode(200)
                .extract()
                .as(LoanResponse.class);
    }
}
