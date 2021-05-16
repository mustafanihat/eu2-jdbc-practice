package apitests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SpartanTestWithParameters {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = "http://54.146.59.25:8000";
    }

    /*
      Given accept type is Json
      And Id parameter value is 5
      When user sends GET request to /api/spartans/{id}
      Then response status code should be 200
      And response content-type: application/json;charset=UTF-8
      And "Blythe" should be in response payload
   */

    @Test
    public void getSpartanId_Positive_PathParam(){
        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id",5)
                .when().get("/api/spartans/{id}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Blythe"));



    }

    /*
   TASK
   Given accept type is Json
   And Id parameter value is 500
   When user sends GET request to /api/spartans/{id}
   Then response status code should be 404
   And response content-type: application/json;charset=UTF-8
   And Spartan Not Found" message should be in response payload
*/

    @Test
    public void getSpartanId_Negative_PathParam(){
        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id",500)
                .when().get("/api/spartans/{id}");
        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Spartan Not Found"));

        response.prettyPrint();


    }
    /*
    Given accept type is Json
    And query parameter values are :
    gender|Female
    nameContains|e
    When user sends GET request to /api/spartans/search
    Then response status code should be 200
    And response content-type: application/json;charset=UTF-8
    And "Female" should be in response payload
    And "Janette" should be in response payload
 */
    @Test
    public void getSpartanId(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","et")
                .and().auth().basic("admin", "admin")
                .when().get("/api/spartans/search");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();

    }

    @Test
    public void positiveTestWithQueryParamWithMaps(){

        Map<String,Object> quaryMap = new HashMap<>();
        quaryMap.put("gender","Female");
        quaryMap.put("nameContains","ett");

        Response response = given().accept(ContentType.JSON).and()
                .auth().basic("admin", "admin")
                .queryParams(quaryMap)
                .when().get("/api/spartans/search");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();









    }

}