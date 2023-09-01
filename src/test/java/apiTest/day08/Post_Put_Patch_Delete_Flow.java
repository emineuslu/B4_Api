package apiTest.day08;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Post_Put_Patch_Delete_Flow {
    static String email = "alok@gmail.com";
    static String password = "Alok1234.";
    static String endPointAddExperience = "/experience/add";
    static String endPointUpdateWithPut = "/experience/updateput";
    static String endPointUpdateWithPatch = "/experience/updatepatch";
    static String endPointDelete = "/experience/delete";
    static Integer experienceId;

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://www.krafttechexlab.com/sw/api/v1";
    }

    //add experience with POST method
    @Test (priority = 1)
    public void addExperience(){

        String jsonBody = "{\n" +
                "  \"job\": \"Junior Developer\",\n" +
                "  \"company\": \"Kraft Techex\",\n" +
                "  \"location\": \"USA\",\n" +
                "  \"fromdate\": \"2020-01-01\",\n" +
                "  \"todate\": \"2022-01-01\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Description\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .header("token", Authorization.getToken(email, password))
                .body(jsonBody)
                .when().log().all()
                .post(endPointAddExperience)
                .prettyPeek();

        //verify that status code is 200
        Assert.assertEquals(response.statusCode(),200);

        //get the experience Id
        experienceId = response.path("id");
    }

    //update experience with PUT method
    @Test (priority = 2)
    public void updateExperience_Put(){

        String jsonBody = "{\n" +
                "  \"job\": \"Junior Developer\",\n" +
                "  \"company\": \"Kraft Techex\",\n" +
                "  \"location\": \"USA\",\n" +
                "  \"fromdate\": \"1999-01-01\",\n" +
                "  \"todate\": \"2000-01-01\",\n" +
                "  \"current\": \"false\",\n" +
                "  \"description\": \"Update with PUT method\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .header("token", Authorization.getToken(email, password))
                .queryParam("id", experienceId)
                .body(jsonBody).log().all()
                .when()
                .put(endPointUpdateWithPut)
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test (priority = 3)
    public void updateExperience_Patch(){

        String jsonBody = "{\n" +
                "  \"company\": \"New Company with PATCH method POSTMAN\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .header("token", Authorization.getToken(email, password))
                .body(jsonBody).log().all()
                .when()
                .patch(endPointUpdateWithPatch + "/" + experienceId)
                .prettyPeek();

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test(priority = 4)
    public void deleteExperience(){
        Response response = RestAssured
                .given()
                .header("token", Authorization.getToken(email, password)).log().all()
                .when()
                .delete(endPointDelete + "/" + experienceId)
                .prettyPeek();
        Assert.assertEquals(response.statusCode(),200);
    }
}
