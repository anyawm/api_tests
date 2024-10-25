package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


import io.restassured.http.ContentType;
import models.lombok.LoginBodyModelLombok;
import models.pojo.LoginBodyModel;
import org.junit.jupiter.api.Test;

public class BookStoreTests {

  @Test
  void getBooks() {
    given()
        .log().uri()
        .get("https://demoqa.com/BookStore/v1/Books")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .body("books.title", hasItems("Git Pocket Guide"));
  }

  @Test
  void postBooksWithoutAuthorization() {
    given()
        .log().uri()
        .post("https://demoqa.com/BookStore/v1/Books")
        .then()
        .log().status()
        .log().body()
        .statusCode(415);
  }

  @Test
  void bookStoreCreateUser() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User9");
    correctData.setPassword("An!!1234");

    // String correctData = "{\"userName\": \"User4\", \"password\": \"An!!1234\"}";
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
        .body("username", is("User9"));
  }

  @Test
  void bookStoreCreateUserLombok() {

    LoginBodyModelLombok correctData = new LoginBodyModelLombok();
    correctData.setUserName("User11");
    correctData.setPassword("An!!1234");

    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().body()
        .log().uri()
        .log().headers()
        .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .log().status()
        .log().body()
        .statusCode(201)
        .body("username", is("User11"));
  }

  @Test
  void bookStoreCreateUserWithAssert() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User10");
    correctData.setPassword("An!!1234");

    LoginBodyModel response = given()
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
        .extract().as(LoginBodyModel.class);
    assertEquals("User10", response.getUserName());
  }

  @Test
  void successfulAuthTest() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User9");
    correctData.setPassword("An!!1234");

    // String correctData = "{\"userName\": \"User4\", \"password\": \"An!!1234\"}";
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

  @Test
  void existsCreateUser() {

    String correctData = "{\"userName\": \"User3\", \"password\": \"An!!1234\"}";
    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().uri()
        .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .log().status()
        .log().body()
        .statusCode(406)
        .body("message", is("User exists!"));
  }

}
