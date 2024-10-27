package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.existUserResponseSpec;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;
import static specs.LoginSpec.missingPasswordResponseSpec;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.lombok.CreateUserResponseModel;
import models.lombok.LoginBodyModelLombok;
import models.lombok.ErrorModel;
import models.pojo.LoginBodyModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookStoreTests {

  @BeforeAll
  public static void setUP() {
    RestAssured.baseURI = "https://demoqa.com";
  }

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
        .log().body()
        .log().uri()
        .log().headers()
        .body(correctData)
        .contentType(ContentType.JSON)
                .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .log().status()
        .log().body()
        .statusCode(201)
        .body("username", is("User11"));
  }

  @Test
  void bookStoreCreateUserSpec() {

    LoginBodyModelLombok correctData = new LoginBodyModelLombok();
    correctData.setUserName("User11");
    correctData.setPassword("An!!1234");

    given(loginRequestSpec)
        .body(correctData)

    .when()
        .post("https://demoqa.com/Account/v1/User")
    .then()
        .spec(loginResponseSpec)
        .body("username", is("User11"));
  }

  @Test
  void missingPasswordCreateUserSpec() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User11");

    ErrorModel response = given(loginRequestSpec)
        .body(correctData)

    .when()
        .post("https://demoqa.com/Account/v1/User")
    .then()
        .spec(missingPasswordResponseSpec)
        .extract().as(ErrorModel.class);
    assertEquals("1200", response.getCode());
    assertEquals("UserName and Password required.", response.getMessage());
  }

  @Test
  void bookStoreCreateUserWithAssert() { //не работает

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User13");
    correctData.setPassword("An!!1234");

    CreateUserResponseModel response = given()
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
        .extract().as(CreateUserResponseModel.class);
    assertEquals("User13", response.getUsername());
    assertEquals("9445f494-f0f9-43fb-b1b8-cf5eb1be715f", response.getUserID());
    assertEquals("", response.getBooks());
  }


  @Test
  void successfulAuthTest() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User9"); //надо добавить генерацию
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

  @Test
  void existsCreateUser() {
    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User9");
    correctData.setPassword("An!!1234");

    ErrorModel response = given(loginRequestSpec)
        .body(correctData)

        .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .spec(existUserResponseSpec)
        .extract().as(ErrorModel.class);
    assertEquals("1204", response.getCode());
    assertEquals("User exists!", response.getMessage());
  }

}
