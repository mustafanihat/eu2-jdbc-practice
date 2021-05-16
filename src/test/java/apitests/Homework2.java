package apitests;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
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
import utilities.ConfigurationReader;

import java.util.List;

public class Homework2 {

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartanapi_url");
    }

    /**
     * Q1:
     * Given accept type is json
     * And path param id is 20
     * When user sends a get request to "/spartans/{id}"
     * Then status code is 200
     * And content-type is "application/json;char"
     * And response header contains Date
     * And Transfer-Encoding is chunked
     * And response payload values match the following:
     * id is 20,
     * name is "Lothario",
     * gender is "Male",
     * phone is 7551551687
     */
    @Test
    public void QuestionOne() {
        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id", 20)
                .when().get("/api/spartans/{id}");
        response.prettyPrint();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        assertTrue(response.headers().hasHeaderWithName("Date"));
        System.out.println(response.getHeaders());
        assertEquals(response.getHeader("Transfer-Encoding"),"chunked");

        assertEquals((int)response.path("id"),20);
        assertEquals(response.jsonPath().getString("name"),"Lothario");
        assertEquals(response.path("gender"),"Male");
        assertEquals(response.jsonPath().getLong("phone"),7551551687l);
    }

    @Test
    public void QuestionOneWithHamcrest(){
        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().pathParam("id", 20)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .assertThat().contentType("application/json;charset=UTF-8")
                .and().headers("Date",notNullValue(),"Transfer-Encoding",equalTo("chunked"))
                .and().assertThat().body("id", equalTo(20),
                "name",equalTo("Lothario"),
                                        "gender",equalTo("Male"),
                                        "phone",equalTo(7551551687l));
    }

    /**
     * Q2:
     * Given accept type is json
     * And query param gender = Female
     * And queary param nameContains = r
     * When user sends a get request to "/spartans/search"
     * Then status code is 200
     * And content-type is "application/json;char"
     * And all genders are Female
     * And all names contains r
     * And size is 20
     * And totalPages is 1
     * And sorted is false
     */
    @Test
    public void QuestionTwo() {
        Response response = given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains", "r")
                .when().get("/api/spartans/search");
        //response.prettyPrint();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");
        List<String> genders = response.jsonPath().getList("content.gender");
        System.out.println(genders.toString());
        for (String gender : genders) {
            assertEquals(gender,"Female");
        }
        List<String> names = response.path("content.name");
        System.out.println(names);
        for (String name : names) {
            assertTrue(name.toLowerCase().contains("r"));
        }
        assertEquals((int)response.path("size"),20);
        assertEquals(response.jsonPath().getInt("totalPages"),1);
        assertEquals(response.jsonPath().getBoolean("pageable.sort.sorted"),false);
    }
    @Test
    public void QuestionTwoWithHamcrest(){
        given().accept(ContentType.JSON)
                .and().auth().basic("admin", "admin")
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains", "r")
                .when().get("/api/spartans/search")
                .then().statusCode(200)
                .assertThat().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("content.gender", hasItems(is("Female")),
                                        "content.name",hasItems(containsStringIgnoringCase("r")),
                                        "size",equalTo(20),
                                        "totalPages",equalTo(1),
                                        "pageable.sort.sorted",equalTo(false));
    }
}
