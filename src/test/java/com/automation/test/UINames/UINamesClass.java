package com.automation.test.UINames;


import com.automation.test.utilities.APIUtilities;
import com.github.javafaker.Faker;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.junit.jupiter.api.BeforeAll;

import com.outomation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class UINamesClass {
@BeforeAll
    public static void setup(){
    baseURI = ConfigurationReader.getProperty("UINames_uri");

}
@Test
    public void no_param_test1(){
    Response response  = given()
            .accept(ContentType.JSON)
            .get().prettyPeek();
    assertEquals(200, response.statusCode());
    assertEquals("application/json; charset=utf-8", response.contentType());

    String UINames = response.getBody().asString();
    System.out.println("UINames");

}
@Test
@DisplayName("Verify that value of gender field is same from step 1")
    public void Gender_Test2(){
    String gender = "male";

    Response response = given().
            accept(ContentType.JSON).
            queryParam("gender", gender).
            when().
            get().prettyPeek();

    response.then().assertThat().
            statusCode(200).
            contentType(ContentType.JSON).
            body("gender", is(gender));

}
@Test
    @DisplayName("Verify that value of region field is same from step 1")
    public void Params_Test3(){

    String region = "Germany";
    String gender = "male";

    Response response = given()
            .accept(ContentType.JSON)
            .queryParam("region", region)
            .queryParam("gender", gender)
            .when()
            .get().prettyPeek();
    response.then().assertThat().statusCode(200).contentType("application/json;charset=utf-8")
            .body("gender", is(gender)).body("region", is(region));

    String Region = response.jsonPath().getString("region");
    System.out.println(Region);

    String Gender = response.jsonPath().getString("gender");
    System.out.println(Gender);


}
@Test
    @DisplayName("Invalid gender Test")
    public void Invalid_Gender_Test4(){
    String gender = "invalid";
    Response response = given()
            .accept(ContentType.JSON)
            .queryParam("gender", gender)
            .get().prettyPeek();

    response.then().assertThat()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("error", containsString("Invalid gender"));
}
@Test
    @DisplayName("Verify that value of error field is Region or language not found")

    public void Invalid_Region_Test5(){

    String region = "Invalid";

    Response response = given()
            .accept(ContentType.JSON)
            .queryParam("region", region)
            .when()
            .get().prettyPeek();

    response.then().assertThat()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("error", containsString("Region or language not found"));


}
@Test
    @DisplayName("Verify that all objects have different name+surname combination")
    public void Amount_Region_Test6(){
    Response response = given()
            .accept(ContentType.JSON)
            .queryParam("region", "turkey")
            .queryParam("gender", "female")
            .queryParam("amount", 20)
            .when()
            .get();

    response.then().assertThat().statusCode(200).contentType(ContentType.JSON);

    List<BIConversion.User> people = response.jsonPath().getList("", BIConversion.User.class);
    for (BIConversion.User user : people) {
        user.getName();

    }
    boolean hasDuplicates = APIUtilities.hasDuplicates(people);
    assertFalse(hasDuplicates, "List has some duplicates");
}
    @Test
    @DisplayName("Verify that all objects the response have the same region and gender passed in step 1")
    public void Same_Region_Gender_Test7() {
        Response response = given().
                accept(ContentType.JSON).
                queryParam("region", "Turkey").
                queryParam("gender", "female").
                queryParam("amount", 20).
                when().
                get().prettyPeek();

        response.then().assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                body("gender", everyItem(is("female"))).
                body("region", everyItem(is("Turkey")));

}
@Test
    @DisplayName("Amount Count Test")
    public void Amount_Count_Test8(){
    int count = 20;
    Response response = given()
            .accept(ContentType.JSON)
            .queryParam("amount", count)
            .when().get().prettyPeek();
    response.then()
            .assertThat()
            .statusCode(200).contentType(ContentType.JSON).
            body("size()", is(count));
}



}
