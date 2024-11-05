package tests;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.registerParser;
import static org.hamcrest.Matchers.is;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import java.util.ArrayList;
import models.pojo.LoginBodyModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateAndAuthTest extends TestBase {



  @Test
  @DisplayName("Создание и авторизация юзера с pojo моделью")
  void createUserAndAuthenticate() {
    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName(GeneratedUser);
    correctData.setPassword("An!!1234");

    // Создание пользователя
    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().body()
        .log().uri()
        .when()
        .post("/Account/v1/User")
        .then()
        .log().status()
        .log().body()
        .statusCode(201)
        .body("username", is(GeneratedUser));

    // Авторизация пользователя
    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().body()
        .log().uri()
        .when()
        .post("/Account/v1/Authorized")
        .then()
        .log().status()
        .log().body()
        .statusCode(200);
  }
}

