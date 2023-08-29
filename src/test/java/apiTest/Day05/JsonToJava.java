package apiTest.Day05;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class JsonToJava {
    String kraftBaseUrl = "https://www.krafttechexlab.com/sw/api/v1";
    String bookStoreBaseUrl = "https://bookstore.toolsqa.com";

    @Test
    public void putAllUsersToList(){
        /**
         * given accept type is JSON
         * And query param pagesize is 50
         * And query param page is 1
         * When user sends a get request to /allusers/alluser
         * Then status code 200
         * put all response body inside a list by as() method
         * make several verifications
         */

        Response response = RestAssured
                .given()
                .accept(ContentType.JSON)
                .queryParam("page", 1)
                .queryParam("pagesize", 50)
                .when()
                .get(kraftBaseUrl + "/allusers/alluser");

        //verify status code
        Assert.assertEquals(response.statusCode(), 200);

        //put all data inside the json body into a list of map
        //de-serilization
        //convert JSON body to Java collection
        List<Map<String,Object>> allUser = response.as(List.class);

        //verify that the email of first user is "afmercan@gmail.com"
        String actualEmail = (String) allUser.get(0).get("email");
        String expectedEmail = "afmercan@gmail.com";
        Assert.assertEquals(actualEmail,expectedEmail);

        //verify that the job of first user is "Manual Tester"
        String actualJob = (String) allUser.get(0).get("job");
        String expectedJob = "Manual Tester";
        Assert.assertEquals(actualJob,expectedJob);

        //verify that the second skill of first user is "Java"
        List<String> skills = (List<String>) allUser.get(0).get("skills");
        String actualSkill = skills.get(1);
        String expectedSkill = "Java";
        Assert.assertEquals(actualSkill,expectedSkill);

        //verify that the third job of the experience of the first user is "Junior Developer"
        List<Map<String,Object>> experience = (List<Map<String, Object>>) allUser.get(0).get("experience");
        String actualJobOfExperience = (String) experience.get(2).get("job");
        String expectedJobOfExperience = "Junior Developer";
        Assert.assertEquals(actualJobOfExperience,expectedJobOfExperience);
    }

    @Test
    public void bookStoreUserTest_NegativeCase(){
        Response response = RestAssured
                .when()
                .get(bookStoreBaseUrl+"/Account/v1/User/1");

        //verify status code
        Assert.assertEquals(response.statusCode(),401);

        //put all data inside the json body into a java collection
        //de-serielization
        //json to java
        Map<String,Object> map = response.as(Map.class);

        //verify that code is 1200
        String actualCode = (String) map.get("code");
        String expectedCode = "1200";
        Assert.assertEquals(actualCode,expectedCode);

        //verify that message is User not authorized!
        String actualMessage = (String) map.get("message");
        String expectedMessage = "User not authorized!";
        Assert.assertEquals(actualMessage,expectedMessage);
    }
}
