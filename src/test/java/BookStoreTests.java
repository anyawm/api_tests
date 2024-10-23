import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;


import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
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
   // Faker faker = new Faker();
   // String userName = faker.name().firstName();
   // String password = "An!!1234";
    String correctData = "{\"userName\": \"User4\", \"password\": \"An!!1234\"}";
    given()
        .body(correctData)
        .contentType(ContentType.JSON)
        .log().uri()
        .when()
        .post("https://demoqa.com/Account/v1/User")
        .then()
        .log().status()
        .log().body()
        .statusCode(201)
        .body("username", is("User4"));
  }

  @Test
  void existsCreateUser() {
    // Faker faker = new Faker();
    // String userName = faker.name().firstName();
    // String password = "An!!1234";
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
