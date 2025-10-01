package com.banco.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * Unit tests for TextConcatenationResource
 */
@QuarkusTest
class TextConcatenationResourceTest {

    @Test
    void testTextConcatenationSuccess() {
        String requestBody = """
            {
                "param1": "Hello",
                "param2": "World",
                "param3": "from",
                "param4": "Quarkus",
                "param5": "API"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/v1/test")
            .then()
            .statusCode(200)
            .body(is("Hello World from Quarkus API"));
    }

    @Test
    void testTextConcatenationWithNullParameter() {
        String requestBody = """
            {
                "param1": "Hello",
                "param2": null,
                "param3": "from",
                "param4": "Quarkus",
                "param5": "API"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/v1/test")
            .then()
            .statusCode(400);
    }

    @Test
    void testTextConcatenationWithBlankParameter() {
        String requestBody = """
            {
                "param1": "Hello",
                "param2": "",
                "param3": "from",
                "param4": "Quarkus",
                "param5": "API"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/v1/test")
            .then()
            .statusCode(400);
    }
}
