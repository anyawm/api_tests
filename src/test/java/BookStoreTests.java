import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;



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
        .statusCode(401)
        .body("message", hasKey("User not authorized!"));
  }

}
