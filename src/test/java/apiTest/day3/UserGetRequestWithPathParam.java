package apiTest.day3;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class UserGetRequestWithPathParam {
    @BeforeClass
    public void beforeClass() {
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    @Test
    public void getUserByID() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 111)
                .when()
                .get("/allusers/getbyid/{id}");
        assertEquals(response.statusCode(),200);
    }

    /** negative get user test
     *  Given accept type is JSON
     *  Ans pathParam id is 444
     *  When the user sends a GET request to "/allusers/getbyid/{id}"
     *  And content-type is "application/json; charset=UTF-8"
     *  And "No User Record Found..." message should be in response payload
     * */

    @Test
    public void getUserByIDWithNegativeScenario() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 444)
                .when()
                .get("/allusers/getbyid/{id}");
        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json; charset=UTF-8");
        Assert.assertTrue(response.body().asString().contains("No User Record Found..."));
    }



}