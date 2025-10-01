package com.banco.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Unit tests for PokemonResource
 */
@QuarkusTest
class PokemonResourceTest {

    @Test
    void testGetPokemonMovesSuccess() {
        given()
            .when()
            .get("/api/v2/move")
            .then()
            .statusCode(200)
            .body("count", notNullValue())
            .body("results", notNullValue());
    }

    @Test
    void testGetPokemonMovesWithHeaders() {
        given()
            .header("X-Request-ID", "test-123")
            .header("User-Agent", "test-client")
            .when()
            .get("/api/v2/move")
            .then()
            .statusCode(200);
    }
}
