package apiTest.day08;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Authorization {

    static String baseUrl = "https://www.krafttechexlab.com/sw/api/v1";
    static String endPoint = "/allusers/login";

    //get token for specific user
    public static String getToken(String email, String password){
        Response response = RestAssured
                .given()
                .formParam("email", email)
                .formParam("password", password)
                .accept(ContentType.JSON).log().all()
                .when()
                .post(baseUrl + endPoint).prettyPeek();
        String token = response.path("token");
        return token;
    }
}
