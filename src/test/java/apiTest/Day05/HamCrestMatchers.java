package apiTest.Day05;
import io.restassured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HamCrestMatchers  {
    @BeforeClass
    public void beforeClass() {
        baseURI="https://www.krafttechexlab.com/sw/api/v1";
    }

    //status code and content type verification
    @Test
    public void hamcrest1(){
        /**
         * TASK
         * given accept type is JSON
         * And path param id is 111
         * When user send a get request to /allusers/getbyid/{id}
         * Then status code should be 200
         * And content type  should be application/json; charset=UTF-8
         */

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("allusers/getbyid/111")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8");
    }

    //response header verifications
    @Test
    public void hamcrest2(){
        /**
         * TASK
         * given accept type is JSON
         * And path param id is 111
         * When user send a get request to /allusers/getbyid/{id}
         * Then status code 200
         * And content Type application/json; charset=UTF-8
         * And response header Content-Length should be 606
         * And response header date is not null
         */

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().log().all()
                .get("allusers/getbyid/111")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .header("Content-Length", Matchers.equalTo("606"))
                .header("Content-Encoding", Matchers.equalTo("gzip"))
                .header("Date", Matchers.notNullValue())
                .log().all();
        //https://www.krafttechexlab.com/sw/api/v1/allusers/getbyid/111

    }

    //response body verifications
    @Test
    public void hamcrest3(){
        /**
         * TASK
         * given accept type is JSON
         * And path param id is 111
         * When user send a get request to /allusers/getbyid/{id}
         * Then status code should be 200
         * And content type  should be application/json; charset=UTF-8
         * make some verification inside the response body
         */

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("allusers/getbyid/111")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .body("id[0]", Matchers.equalTo(111),
                        "name[0]", Matchers.equalTo("Thomas Eduson"),
                        "job[0]", Matchers.equalTo("Developer"),
                        "skills[0][4]", Matchers.equalTo("SQL"),
                        "education[0].school[0]", Matchers.equalTo("ODTU"));

    }

    //verify one item in the list
    @Test
    public void hamcrest4(){
        /**
         * given accept type is JSON
         * And query param pagesize is 50
         * And query param page is 1
         * When user sends a get request to /allusers/alluser
         * Then status code 200
         * And content Type application/json; charset=UTF-8
         * Verify that one of the email is "ghan@krafttechexlab.com"
         */

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .queryParam("page", 1)
                .queryParam("pagesize", 50)
                .when()
                .get("/allusers/alluser")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .body("email", Matchers.hasItem("ghan@krafttechexlab.com"));
    }

    //verify multiple items in one shot
    @Test
    public void hamcrest5(){
        /**
         * given accept type is JSON
         * And query param pagesize is 50
         * And query param page is 1
         * When user sends a get request to /allusers/alluser
         * Then status code 200
         * And content Type application/json; charset=UTF-8
         * Verify that email has "ghan@krafttechexlab.com","blackuncle9599@gmail.com","user33@test.com","sekercikefe@gmail.com","eddiem@kraft.com","qateam@test.com"
         */

        RestAssured
                .given()
                .accept(ContentType.JSON)
                .queryParam("page", 1)
                .queryParam("pagesize", 50)
                .when()
                .get("/allusers/alluser")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .body("email", Matchers.hasItems("ghan@krafttechexlab.com","blackuncle9599@gmail.com","user33@test.com","sekercikefe@gmail.com","eddiem@kraft.com","qateam@test.com"));
    }

    @Test
    public void getOneUserWithHamcrestMatchers() {


        /**
         *         given accept type is json
         *         And path param id is 62
         *         When user sends a get request to /allusers/getbyid/{id}
         *         Then status code should be 200
         *         And content type should be "application/json; charset=UTF-8"
         *         user's id should be "62"
         *         user's name should be "Selim Gezer"
         *         user's job should be "QA Automation Engineer"
         *         User's second skill should be "Selenium"
         *         User's third education school name should be "Ankara University"
         *         The response header Content-Lenght should be 756
         *         User's email should be "sgezer@gmail.com"
         *         User's company should be "KraftTech"
         *         Response headers should have "Date" header
         *
         */


        given().accept(ContentType.JSON)
                .pathParam("id",62)
                .get("/allusers/getbyid/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType("application/json; charset=UTF-8")
                .and()
                .body("[0].id",Matchers.equalTo(62),
                        "name[0]",Matchers.equalTo("Selim Gezer"),
                        "[0].job",Matchers.equalTo("QA Automation Engineer"),
                        "skills[0][1]",Matchers.equalTo("Selenium"),
                        "[0].education[2].school",Matchers.equalTo("Ankara University"))
                .and()
                .header("Content-Length","756")
                .header("Content-Length",Matchers.equalTo("756"))
                .headers("Content-Length","756")
                .headers("Date",Matchers.notNullValue())
                .header("Date",Matchers.notNullValue())
                .body("skills[0]",Matchers.hasItem("Selenium")
                        ,"[0].skills",Matchers.hasItems("Selenium","Java"));


    }

}
