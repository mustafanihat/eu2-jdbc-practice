package apitests;

import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import utilities.ConfigurationReader;
public class Homework1 {
    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi_url");
    }
/**
//    Q1:
//            - Given accept type is Json
//- Path param value- US
//- When users sends request to /countries
//- Then status code is 200
//            - And Content - Type is Json
//- And country_id is US
//- And Country_name is United States of America
//- And Region_id is
 */
@Test
public void Test1(){
    Response response = given().accept(ContentType.JSON)
            .and().pathParam("country_id","US")
            .when().get("/countries/{country_id}");
    response.prettyPrint();
    assertEquals(response.statusCode(),200);
    assertEquals(response.contentType(),"application/json");
    assertTrue(response.asString().contains("United States of America"));
    assertEquals(response.path("country_name"),"United States of America");
    assertEquals(response.jsonPath().getString("country_name"),"United States of America");
    System.out.println("region_id) = " + response.path("region_id"));
    int region=response.path("region_id");
    assertEquals(region,2);
    assertEquals(response.jsonPath().getString("region_id"),"2");
}
    @Test
    public void QuestionOneWithHamcrest(){
        given().accept(ContentType.JSON)
                .and().pathParam("country_id","US")
                .when().get("/countries/{country_id}")
                .then().statusCode(200)
                .assertThat().contentType("application/json")
                .and().assertThat().body("country_id", equalTo("US"),
                "country_name",equalTo("United States of America"),
                                        "region_id",equalTo(2));
    }
    /**
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     */
    @Test
    public void Test2(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q", "{\"department_id\": 80}")
                .when().get("/employees");
        response.prettyPrint();
        assertEquals(response.contentType(),"application/json");
        assertEquals(response.statusCode(),200);
        JsonPath jsonPath=response.jsonPath();
        List<String> jobIDs1 = response.path("items.job_id");
        List<String> jobIDs = jsonPath.getList("items.job_id");
        List<Integer> depIds =jsonPath.getList("items.department_id");
        System.out.println("jobIDs = " + jobIDs);
        System.out.println("+++++++++++++++");
        System.out.println(jobIDs1.toString());
        for (String jobId : jobIDs1) {
            assertTrue(jobId.startsWith("SA"));
        }
        for (Integer depid : depIds) {
            assertTrue(depid.equals(80));
        }

        for (String jobID : jobIDs) {
            assertTrue(jobID.startsWith("SA"));
        }
        assertEquals(jobIDs.size(),25);
        int size=response.path("count");
        System.out.println(size);
    }
    @Test
    public void QuestionTwoWithHamcrest(){
        given().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\": 80}")
                .when().get("/employees")
                .then().statusCode(200)
                .assertThat().contentType("application/json")
                .and().assertThat().body("items.job_id",hasItems(startsWith("SA"))
                                        ,"items.department_id",hasItems(is(80))
                                        ,"count",is(25));
    }

/**
 * Q3:
 * - Given accept type is Json
 * -Query param value q= region_id 3
 * - When users sends request to /countries
 * - Then status code is 200
 * - And all regions_id is 3
 * - And count is 6
 * - And hasMore is false
 * - And Country_name are;
 * Australia,China,India,Japan,Malaysia,Singapore
 */

@Test
public void test3() {
    Response response = given().accept(ContentType.JSON).and()
            .queryParam("q", "{\"region_id\": 3}")
            .when().get("/countries");
    response.prettyPrint();
    assertEquals(response.contentType(),"application/json");
    assertEquals(response.statusCode(),200);
    List<Integer> regionIDs = response.path("items.region_id");
    System.out.println(regionIDs.toString());
    for (int regionID : regionIDs) {
        assertEquals(regionID,3);
    }
    assertEquals(regionIDs.size(),6);
    Boolean hasMore =response.jsonPath().getBoolean("hasMore");
    System.out.println(hasMore);
    assertFalse(hasMore);
   // assertEquals(hasMore,"false");
    List<String> country_names = response.jsonPath().getList("items.country_name");
    System.out.println(country_names);
    List<String> expected_country_names = new ArrayList<>(Arrays.asList("Australia", "China", "India", "Japan", "Malaysia", "Singapore"));

    assertEquals(country_names,expected_country_names);


}
    @Test
    public void QuestionThreeWithHamcrest(){
        given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\": 3}")
                .when().get("/countries")
                .then().statusCode(200)
                .assertThat().contentType("application/json")
                .and().assertThat().body("items.region_id",hasItems(3)
                ,"count",is(6)
                ,"hasMore",equalTo(false)
                ,"items.country_name",hasItems("Australia", "China", "India", "Japan", "Malaysia", "Singapore"));
    }
}
