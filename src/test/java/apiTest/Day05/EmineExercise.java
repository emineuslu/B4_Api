package apiTest.Day05;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;

public class EmineExercise {
    @BeforeClass
    public void beforeClass() {
        baseURI = "https://bookcart.azurewebsites.net";
    }


    @Test
    public void exerciseE() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("id", 5)
                .when()
                .get("api/Book/{id}")
                .then()
                .statusCode(200)
                .contentType(" application/json; charset=utf-8")
                .header("Content-Length", Matchers.equalTo("247"))
                .header("Content-Encoding", Matchers.equalTo("gzip"));



    }

    /**Class Task
     * Given accept type JSON
     * and Query parameter value pagesize 50
     * and Query parameter value page 1
     * When user send GET request to /allusers/alluser
     * Then response status code is 200
     * And response content type is "application/json; charset=UTF-8"
     * Verify that first id 1
     * verify that first name MercanS
     * verify that last id is 102
     * verify that last name is GHAN
     */







}
