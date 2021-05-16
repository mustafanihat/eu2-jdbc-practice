package Day8;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

public class BookItAuthTest {

    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOTkxIiwiYXVkIjoic3R1ZGVudC10ZWFtLWxlYWRlciJ9.PejlP7F8p8bA9BGqol3jfWESaPRP5lBkIy7q2huN-4s";

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = "https://cybertek-reservation-api-qa.herokuapp.com";
    }

    @Test
    public void getToken() {
        Response response = given().queryParam("email", "sbirdbj@fc2.com")
                .and().queryParam("password", "asenorval")
                .get("/sign");
        response.prettyPrint();

        String accessToken2 = "Bearer "+response.path("accessToken");
        System.out.println(accessToken2);

        Response response2 = given().header("Authorization", accessToken2)
                .when().get("/api/campuses");

        response2.prettyPrint();
    }

    @Test
    public void getAllCampuses() {
        Response response = given().header("Authorization", accessToken)
                .when().get("/api/campuses");

        response.prettyPrint();
    }
}
