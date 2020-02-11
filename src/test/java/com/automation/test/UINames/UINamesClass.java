package com.automation.test.UINames;


import com.github.javafaker.Faker;
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
    public void no_param_test(){
    Response response  = given()
            .accept(ContentType.JSON)
            .get("/api/").prettyPeek();
    assertEquals(200, response.statusCode());
    assertEquals("application/json; charset=utf-8", response.contentType());

    String UINames = response.getBody().asString();
    System.out.println("UINames");

}
@Test
@DisplayName("Verify that value of gender field is same from step 1")
    public void Gender_Test(){
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



}
