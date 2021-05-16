package Day7_HarryPotter;
import static org.testng.Assert.*;

import java.util.List;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.*;

import utilities.ConfigurationReader;

import java.util.*;

public class POJO_Harry {
    String apiKey = "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae";

    @BeforeClass
    public void beforeClass(){
        RestAssured.baseURI = ConfigurationReader.getProperty("harry_url");
    }

    /**
     *     1. Send a get request to /sortingHat. Request includes :
     *     2. Verify status code 200, content type application/json; charset=utf-8
     *     3. Verify that response body contains one of the following houses:
     *     "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     *     GET /sortingHat
     *     Returns a random Hogwarts House.
     *     This is the only route that does not require a key.
     */
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/sortingHat");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<String> houses = new ArrayList<>(Arrays.asList("Gryffindor","Ravenclaw","Slytherin","Hufflepuff"));
        System.out.println(houses);
        int i=0;
        String body = response.body().asString();
        System.out.println(body);
        for (String house : houses) {
            if(house.equals(body.substring(1,body.length()-1))){
                i++;
            }
        }
        assertEquals(i,1);
        List<String> expectedNames= Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff");
        assertTrue(expectedNames.contains(body.substring(1,body.length()-1)));

    }

    /**
     * 1. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value invalid
     * 2. Verify status code 401, content type application/json; charset=utf-8
     * 3. Verify response status line include message Unauthorized
     * 4. Verify that response body says "error": "API Key Not Found"
     */
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "invalid")
                .when().get("/characters");
        assertEquals(response.statusCode(),401);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertTrue(response.statusLine().contains("Unauthorized"));
        assertTrue(response.body().asString().contains("\"error\":\"API Key Not Found\""));

        given().accept(ContentType.JSON)
                .and().queryParam("key","invalid")
                .when().get("/characters")
                .then().assertThat().statusCode(401)
                .and().contentType("application/json; charset=utf-8")
                .and().statusLine(containsStringIgnoringCase("Unauthorized"))
                .and().body("error",equalTo("API Key Not Found"));

    }
    /**
     * 1. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * 2. Verify status code 409, content type application/json; charset=utf-8
     * 3. Verify response status line include message Conflict
     * 4. Verify that response body says "error": "Must pass API key for request"
     */
    @Test
    public void test3() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/characters");
        assertEquals(response.statusCode(),409);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertTrue(response.statusLine().contains("Conflict"));
        assertTrue(response.body().asString().contains("\"error\":\"Must pass API key for request\""));

        given().accept(ContentType.JSON)
                .when().get("/characters")
                .then().assertThat().statusCode(409)
                .contentType("application/json; charset=utf-8")
                .statusLine(containsString("Conflict"))
                .body("error",equalTo("Must pass API key for request"));
    }

    /**
     * 1. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify response contains 194 characters
     */
    @Test
    public void test4() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .when().get("/characters");
        response.then().assertThat().statusCode(200)
                .and().contentType("application/json; charset=utf-8")
                .body("_id",hasSize(195));

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        List<String> names = response.path("name");
        System.out.println(names.size());
        assertEquals(names.size(),195);
        //response.prettyPrint();
        //Character[] characters = response.body().as(Character[].class);

        List<Character> characters = response.body().as(List.class);

        assertEquals(characters.size(),195);
    }



    /**
     * 1. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify all characters in the response have id field which is not empty
     * 4. Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
     * 5. Verify value of the house in all characters in the response is one of the following:
     * "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */
    @Test
    public void test5_1() {
        given().accept(ContentType.JSON)
                .and().queryParam("key",apiKey)
                .when().get("/characters")
                .then().assertThat().statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("_id",hasItems(notNullValue()),
                        "dumbledoresArmy",hasItems(true,false),
                        "house",hasItems("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"));
    }

    @Test
    public void test5() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .when().get("/characters");
        response.prettyPrint();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        List<String> ids = response.path("_id");
        for (String id : ids) {
            assertFalse(id.equals(""));
        }
        List<Boolean> armys= response.path("dumbledoresArmy");
        for (boolean army : armys) {
            assertTrue(army==true||army==false);
        }
        List<String> actualHouses= response.path("house");
        List<String> expectedHouses = new ArrayList<>(Arrays.asList("Gryffindor","Ravenclaw","Slytherin","Hufflepuff"));
        System.out.println(actualHouses);
        System.out.println(actualHouses.size());
        for (String actualHouse : actualHouses) {
            int i=0;
            if(actualHouse!=null) {
                for (String house : expectedHouses) {

                    if (house.equals(actualHouse)) {
                        i++;
                    }
                }
                assertEquals(i, 1);
            }
        }
    }

    /**
     * 1. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Select name of any random character
     * 4. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Query param name with value from step 3
     * 5. Verify that response contains the same character information from step 3. Compare all fields.
     */
    @Test
    public void characterInformation6() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("key", apiKey)
                .when().get("/characters");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<Character> expectedCharacters = response.body().as(List.class);
        List<String> nameList = response.path("name");
        System.out.println(expectedCharacters.size());
        //System.out.println("nameList.size() = " + nameList.size());

        Random rn = new Random();
        int randomNumber = rn.nextInt(nameList.size()+1) ;
        String randomName = nameList.get(randomNumber);


        Response response2 = given().accept(ContentType.JSON)
                .queryParam("key", apiKey)
                .queryParam("name", randomName)
                .when().get("/characters");

        List<Character> actualCharacters = response2.body().as(List.class);

        System.out.println("expectedCharacters.get("+randomNumber+") = " + expectedCharacters.get(randomNumber));
        System.out.println("actualCharacters.get(0) = " + actualCharacters.get(0));
        assertEquals(expectedCharacters.get(randomNumber),actualCharacters.get(0));
        System.out.println(actualCharacters.get(0).getName());

    }

    @Test
    public void test6() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .when().get("/characters");
        //response.prettyPrint();
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<Character> characters = response.body().as(List.class);

        Random rn = new Random();
        int randomNum = rn.nextInt(characters.size());
        System.out.println(characters.size());
//        String randomName = (String) characters.get(randomNum).getName();
        String name = characters.get(randomNum).getName();
        System.out.println(name);
//        Character[] actualCharacter = response2.body().as(Character[].class);
//        System.out.println("expected:"+characters.get(randomNum));
//        System.out.println("actual  :"+actualCharacter[0]);
//        assertEquals(actualCharacter[0],characters.get(randomNum));

    }



    /**
     * Verify name search
     * 1. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Query param name with value Harry Potter
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Verify name Harry Potter
     * 4. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Query param name with value Marry Potter
     * 5. Verify status code 200, content type application/json; charset=utf-8
     * 6. Verify response body is empty
     */
    @Test
    public void test7_1() {
        given().accept(ContentType.JSON)
                .and().queryParam("key",apiKey)
                .queryParam("name","Harry Potter")
                .when().get("/characters")
                .then().assertThat().statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("name[0]",equalTo("Harry Potter"));

        given().accept(ContentType.JSON)
                .and().queryParam("key",apiKey)
                .queryParam("name","Marry Potter")
                .when().get("/characters")
                .then().assertThat().statusCode(200)
                .contentType("application/json; charset=utf-8")
                .body("",hasSize(0));
    }

    @Test
    public void test7() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .and().queryParam("name","Harry Potter")
                .when().get("/characters");
        response.prettyPrint();

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<Character> characters = response.body().as(List.class);

        assertEquals(characters.get(0).getName(),"Harry Potter");

        Response response2 = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .and().queryParam("name","Marry Potter")
                .when().get("/characters");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        assertEquals(response2.body().asString(),"[]");

        List<Character> characters2 = response2.body().as(List.class);

        assertEquals(characters2.size(),0);

    }

    /**
     * Verify house members
     * 1. Send a get request to /houses. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * 2. Verify status code 200, content type application/json; charset=utf-8
     * 3. Capture the id of the Gryffindor house
     * 4. Capture the ids of the all members of the Gryffindor house
     * 5. Send a get request to /houses/:id. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Path param id with value from step 3
     * 6. Verify that response contains the same member ids as the step 4
     */

    @Test
    public void test8_1() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", apiKey)
                .when().get("/houses");

        House[] houses = response.body().as(House[].class);
        response.then().assertThat().statusCode(200)
                .and().contentType("application/json; charset=utf-8");
        String gryffindorId=null;
        List<String> expectedMemberIds=null;
        for (House house : houses) {
            if(house.getName().equals("Gryffindor")){
                gryffindorId=house.get_id();
                expectedMemberIds=house.getMembers();
            }
        }
        System.out.println(gryffindorId);
        Response response2 = given().accept(ContentType.JSON)
                .and().queryParam("key", apiKey)
                .and().pathParam("id",gryffindorId)
                .when().get("/houses/{id}");
        HouseSingle[] houseSingles = response2.body().as(HouseSingle[].class);

        System.out.println(expectedMemberIds);
        List<Member> members = houseSingles[0].getMembers();
        System.out.println(members.size()+""+expectedMemberIds.size());
        for (int i = 0; i < members.size(); i++) {
            assertEquals(members.get(i).get_id(),expectedMemberIds.get(i));
        }
    }

    @Test
    public void test8() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .when().get("/houses");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        //response.prettyPrint();
        List<String> houses = response.path("name");
        String gryffindorId2 = response.jsonPath().getString("findAll{it.name==\"Gryffindor\"}._id[0]");
        int index= 0;
        for (int i=0;i<houses.size();i++) {
            if(houses.get(i).equals("Gryffindor")){
                index=i;
            }
        }
        List<String> memberIds = response.path("members["+index+"]");
        System.out.println(memberIds);
        String gryffindorId = response.path("_id["+index+"]") ;
        System.out.println(gryffindorId+"*****"+gryffindorId2);
//        House[] houses = response.body().as(House[].class);
//
//        System.out.println(houses[0].getMembers().toString());
//        List<String> expectedMembers = houses[index].getMembers();
        Response response2 = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .and().pathParam("id",gryffindorId)
                .when().get("/houses/{id}");
        response2.prettyPrint();

        List<String> actualIds= response2.path("members[0]._id");

        System.out.println(actualIds);

        assertEquals(memberIds,actualIds);

//        HouseSingle[] houseSingle = response2.body().as(HouseSingle[].class);
//        System.out.println(houseSingle[0].getMembers().toString());
//        List<Member> members = houseSingle[0].getMembers();
//        System.out.println(members.size()+"  "+expectedMembers.size());
//        for (int i =0 ; i<members.size();i++) {
//            assertEquals(members.get(i).getId(),expectedMembers.get(i));
//        }

    }
    /**
     * 1. Send a get request to /houses/:id. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Path param id with value 5a05e2b252f721a3cf2ea33f
     * 2. Capture the ids of all members
     * 3. Send a get request to /characters. Request includes :
     * • Header Accept with value application/json
     * • Query param key with value {{apiKey}}
     * • Query param house with value Gryffindor
     * 4. Verify that response contains the same member ids from step 2
     */
    @Test
    public void test9() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .and().pathParam("id","5a05e2b252f721a3cf2ea33f")
                .when().get("/houses/{id}");

        List<String> expectedIds= response.path("members[0]._id");

        Response response2 = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .and().queryParam("house","Gryffindor")
                .when().get("/characters");
        response2.then().assertThat().body("_id",hasItems("5a0fa648ae5bc100213c2332","5a0fa67dae5bc100213c2333","5a1096b33dc2080021cd8762"));
        //response2.then().assertThat().body("_id",hasItems(expectedIds.toString()));
        //response2.prettyPrint();
//        Character character = response2.body().as(Character.class);
//        System.out.println(character.getId());
        List<String> actualIds=response2.path("_id");

        for (String expectedId : expectedIds) {
            int counter=0;
            for (String actualId : actualIds) {
                if(expectedId.equals(actualId)){
                    counter++;
                }
            }
            assertEquals(counter,1);
        }
        System.out.println(expectedIds.size() +"actual>"+actualIds.size());
        System.out.println(expectedIds);
        System.out.println(actualIds);
        //assertEquals(actualIds,expectedIds,"verify ids");
    }

    @Test
    public void test10() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("key", "$2a$10$ekMnS3BxAuNPYEplyKENTOM3F3kpElN/.RPdV8A4XHFWfRT9IhJae")
                .when().get("/houses");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<String> houses = response.path("name");
        int gryffindorMembers=0,ravenclawMembers=0,slytherinMembers=0 , hufflepuffMembers=0;
        for (int i=0;i<houses.size();i++) {
            if(houses.get(i).equals("Gryffindor")){
                List<String> gryffindorMemberIds = response.path("members["+i+"]");
                gryffindorMembers = gryffindorMemberIds.size();
            }else if(houses.get(i).equals("Ravenclaw")){
                List<String> ravenclawMemberIds = response.path("members["+i+"]");
                ravenclawMembers = ravenclawMemberIds.size();
            }else if(houses.get(i).equals("Slytherin")){
                List<String> slytherinMemberIds = response.path("members["+i+"]");
                slytherinMembers = slytherinMemberIds.size();
            }else if(houses.get(i).equals("Hufflepuff")){
                List<String> hufflepuffMemberIds = response.path("members["+i+"]");
                hufflepuffMembers = hufflepuffMemberIds.size();
            }
        }
        assertTrue(gryffindorMembers>ravenclawMembers && gryffindorMembers>slytherinMembers && gryffindorMembers>hufflepuffMembers);

    }
}
