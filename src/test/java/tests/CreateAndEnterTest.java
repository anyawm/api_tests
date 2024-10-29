package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import models.pojo.LoginBodyModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateAndEnterTest {
  static String GeneratedUser;

  @BeforeAll
  public static void setUP() {
    RestAssured.baseURI = "https://demoqa.com";
    RestAssured.requestSpecification = new RequestSpecBuilder()
        .log(LogDetail.ALL)
        .build();
    RestAssured.responseSpecification = new ResponseSpecBuilder()
        .log(LogDetail.BODY)
        .log(LogDetail.STATUS)
        .build();

    Faker faker = new Faker();
    GeneratedUser = faker.name().firstName();
  }


  @Test
  @DisplayName("Создание юзера с pojo моделью")
  void bookStoreCreateUser() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName(GeneratedUser);
    correctData.setPassword("An!!1234");

    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().body()
        .log().uri()
        .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .log().status()
        .log().body()
        .statusCode(201)
        .body("username", is(GeneratedUser));
  }

  @Test
  void successfulAuthTest() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName(GeneratedUser); // как сделать чтобы я сгенерировала имя и оно могло использоваться тут
    // я сохраняю в тесте на создание в переменную, но не получается ее больше нигде использовать
    correctData.setPassword("An!!1234");

    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().body()
        .log().uri()
        .when()
        .post("https://demoqa.com/Account/v1/Authorized")
        .then()
        .log().status()
        .log().body()
        .statusCode(200);
  }

}
