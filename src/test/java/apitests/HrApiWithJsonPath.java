package apitests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;
import io.restassured.RestAssured;
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

import java.util.List;

public class HrApiWithJsonPath {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi_url");
    }

    @Test
    public void CountriesWithJsonPath(){
        //request
        Response response = get("/countries");

        //assign response body to jsonpath
        JsonPath json = response.jsonPath();

        //ready second country name
        String secondCountryName = json.getString("items.country_name[1]");
        System.out.println("secondCounttyName = " + secondCountryName);


        //get all country ids
        List<String> allCountryIds = json.getList("items.country_id");
        System.out.println(allCountryIds);

        //get all country names where their region id is equal to 2
        List<String> CountryNameWithRegion2 = json.getList("items.findAll {it.region_id==2}.country_name");

        System.out.println(CountryNameWithRegion2);

    }
    @Test
    public void findAllEmployeesExample(){
        ///request
        Response response = given().queryParam("limit", 107)
                //.and().pathParam("limit",107)
                //.and().param("asd",123)
                .get("/employees");

        assertEquals(response.statusCode(),200);

        JsonPath json = response.jsonPath();

        //get me all firstname of employees who is making more than 10000
        List<String> employeesName = json.getList("items.findAll {it.salary>10000}.first_name");
        System.out.println(employeesName);

        //get me all emails who is working as IT_PROG;
        List<String> emailList = json.getList("items.findAll {it.job_id==\"IT_PROG\"}.email");
        System.out.println(emailList);


        //find the firstname of who is making highest salary
        String kingName = json.getString("items.max {it.salary}.first_name");
        System.out.println("kingName = " + kingName);
    }
}
