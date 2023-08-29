package apiTest.day3;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


public class UserGetRequestAndVerifyHeaders {
    @BeforeClass
    public void beforeClass() {
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }
    /** Headers test
     *  Given accept type is JSON
     *  Ans pathParam id is 111
     *  When the user sends a GET request to "/allusers/getbyid/{id}"
     *  And content-type is "application/json; charset=UTF-8"
     *
     * */

    @Test
    public void headerTest() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 111)
                .when()
                .get("/allusers/getbyid/{id}");
        //response.prettyPrint();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json; charset=UTF-8");

        //verify the header
        Assert.assertEquals(response.header("Content-Length"),"606");
        Assert.assertEquals(response.header("Content-Type"),"application/json; charset=UTF-8");
        assertTrue(response.headers().hasHeaderWithName("Date"));
    }
}
