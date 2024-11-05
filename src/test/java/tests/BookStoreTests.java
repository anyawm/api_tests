package tests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.createResponseSpec;
import static specs.LoginSpec.existUserResponseSpec;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;
import static specs.LoginSpec.missingPasswordResponseSpec;


import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import models.lombok.CreateUserResponseModel;
import models.lombok.LoginBodyModelLombok;
import models.lombok.ErrorModel;
import models.pojo.LoginBodyModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookStoreTests extends TestBase {

   @Test
  @DisplayName("Создание юзера с pojo моделью")
  void bookStoreCreateUser() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName(GeneratedUser);
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
        .body("username", is(GeneratedUser));
  }

  @Test
  @DisplayName("Создание юзера с lombok моделью только на request")
  void bookStoreCreateUserLombok() {

    LoginBodyModelLombok correctData = new LoginBodyModelLombok();
    correctData.setUserName(GeneratedUser);
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
        .body("username", is(GeneratedUser));
  }


  @Test
  @DisplayName("Создание юзера с существующими данными с моделями на request и response + spec")
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

  @Test
  @DisplayName("Создание юзера без пароля")
  void missingPasswordCreateUserSpec() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName(GeneratedUser);

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
  @DisplayName("Создание юзера со spec")
  void bookStoreCreateUserSpec() {


    LoginBodyModelLombok correctData = new LoginBodyModelLombok();
    correctData.setUserName(GeneratedUser);
    correctData.setPassword("An!!1234");


    given(loginRequestSpec)
        .body(correctData)

        .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .spec(createResponseSpec)
        .body("username", is(GeneratedUser));
  }

  @Test
  void successfulAuthTest() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName(GeneratedUser); // я сдаюсь
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
  @DisplayName("Создание юзера с моделями на request и response + spec / не работает")
  void bookStoreCreateUserWithAssert() {

    LoginBodyModel correctData = new LoginBodyModel();
    correctData.setUserName("User26");
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
    assertEquals("User26", response.getUsername());

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
  void testGroovyGetBookAuthor() {
    Response response = get("/BookStore/v1/Book?ISBN=9781449331818");
    String author = response.path("author");
    System.out.println(author);
  }

  @Test
  void testGroovyGetBookPublisher() {
    String publisher = get("/BookStore/v1/Book?ISBN=9781449331818").path("publisher");
    System.out.println(publisher);
  }

  @Test
  void testGroovyGetBook() {

    given()
        .log().uri()
        .log().body()
        .log().headers()
        .when()
        .get("/BookStore/v1/Book?ISBN=9781449331818")
        .then()
        .log().body()
        .log().status()
        .statusCode(200)
        .body("publisher", equalTo("O'Reilly Media"));
  }

  @Test
  void testGroovyGetOneOfBookAuthor() {
    Response response = get("/BookStore/v1/Books");
    String author = response.path("books.author[0]");
    System.out.println(author);
  }

  @Test
  void testGroovyGetBooksAuthors() {
    Response response = get("/BookStore/v1/Books");
    List<String> allAuthors = response.path("books.author");
    System.out.println(allAuthors);
  }

  @Test
  void testGroovyGetBooks() {
    Response response = get("/BookStore/v1/Books");
    List<Map<String, ?>> allBooksData = response.path("books");
    System.out.println(allBooksData);
  }

  @Test
  void findAllDataAboutBook() {
    Response response = get("/BookStore/v1/Books");
    Map<String, ?> allBookInfo = response.path("books.find {it.title = 'Git Pocket Guide'}");
    System.out.println(allBookInfo);
  }

  @Test
  void findThePublisherBook() {
    Response response = get("/BookStore/v1/Books");
    String thePublisherBook = response.path("books.find {it.isbn = '9781449325862'}.publisher");
    System.out.println(thePublisherBook);
  }

  @Test
  void findBigBooksTitle() {
    Response response = get("/BookStore/v1/Books");
    List<String> bigBooks = response.path("books.findAll {it.pages > 300}.title");
    System.out.println(bigBooks);
  }

  @Test
  void findTheFattestBook() {
    Response response = get("/BookStore/v1/Books");
    String fattestBook = response.path("books.max {it.pages}.title");
    System.out.println(fattestBook);
  }

  @Test
  void findTheSmallestBook() {
    Response response = get("/BookStore/v1/Books");
    String smallestBook = response.path("books.min {it.pages}.title");
    System.out.println(smallestBook);
  }

  @Test
  void findTheShortestDescription() {
    Response response = get("/BookStore/v1/Books");
    String shortestDesc = response.path("books.min {it.description.length()}.title");
    System.out.println(shortestDesc);
  }

  @Test
  void sumOfPages() {
    Response response = get("/BookStore/v1/Books");
    Integer sumAllPages = response.path("books.collect {it.pages}.sum()");
    System.out.println(sumAllPages);
  }

  @Test
  void countOfBooks() {
    Response response = get("/BookStore/v1/Books");
    Integer countOfBooks = response.path("books.collect {it.title}.size()");
    System.out.println(countOfBooks);
  }

  @Test
  void extractBooksOfOnePublisherAndAuthor() {
    Response response = get("/BookStore/v1/Books");
    Map<String, ?> OneAuthorBooks = response.path("books.findAll {it.publisher = 'No Starch Press' }.find{it.author = 'Marijn Haverbeke'}");
    System.out.println(OneAuthorBooks);
  }

}

