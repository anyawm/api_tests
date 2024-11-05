package tests;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

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

}
