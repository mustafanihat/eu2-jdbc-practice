package apitests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpartanGetRequest {

    String spartanurl = "http://54.146.59.25:8000";

    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON).and()
                .auth().basic("admin","admin")
                .when().get(spartanurl +"/api/spartans");
        System.out.println(response.statusCode());

        response.prettyPrint();

    }

    /*
   When users sends a get request to /api/spartans/3
   Then status code should be 200
   And content type should be application/json;charset=UFT-8
   and json body should contain Fidole
*/

    @Test
    public void test2() {

        Response response = given().accept(ContentType.JSON).and()
                .auth().basic("admin","admin")
                .when().get(spartanurl +"/api/spartans/3");
        System.out.println(response.statusCode());
        Assert.assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");

        Assert.assertTrue(response.body().asString().contains("Fidole"));

        response.prettyPrint();

    }
    @Test
    public void test2_1() {
        given().auth().basic("admin", "admin").when().get(spartanurl+"/api/spartans/3").
                then().assertThat().statusCode(200).
                and().contentType("application/json;charset=UTF-8").and().toString().contains("Fidole");

    }
    /*
     Given no headers provided
     When Users sends GET request to /api/hello
     Then response status code should be 200
     And Content type header should be "text/plain;charset=UTF-8"
     And header should contain date
     And Content-Length should be 17
     And body should be "Hello from Sparta"
     */
    @Test
    public void test3() {

        Response response = given().auth().basic("admin","admin")
                .when().get(spartanurl +"/api/hello");
        System.out.println(response.statusCode());
        assertEquals(response.statusCode(),200);

        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

        System.out.println(response.header("Date"));
        System.out.println(response.header("Content-Length"));

        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertTrue(response.headers().hasHeaderWithName("Content-Length"));

        assertEquals(response.header("Content-Length"),17);
        assertEquals(response.body().asString().length(),17);

        assertTrue(response.body().asString().contains("Hello from Sparta"));



        response.prettyPrint();

    }
}
