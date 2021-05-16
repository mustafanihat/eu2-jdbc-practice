package apitests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;

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

public class CBTrainingWithJsonPath {
    @BeforeClass
    public void beforeClass(){
        baseURI = ConfigurationReader.getProperty("cbtapi_url");
    }

    @Test
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",12361)
                .when().get("/student/{id}");

        //verify status code
        assertEquals(response.statusCode(),200);

        //assign response to jsonpath
        JsonPath json = response.jsonPath();

        //get values with jsonpath
        System.out.println(json.getString("students.firstName[0]"));

        String lastName = json.getString("students.lastName[0]");
        System.out.println("lastName = " + lastName);

        String phone = json.getString("students.contact[0].phone");
        System.out.println("phone = " + phone);

        //city and zipcode
        String city = json.getString("students.company[0].address.city");
        System.out.println("city = " + city);

        int zipCode = json.getInt("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);

        //some assertions
        assertEquals(city,"McLean");
        assertEquals(zipCode,22102);




    }


}