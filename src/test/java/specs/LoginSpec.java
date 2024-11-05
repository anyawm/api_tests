package specs;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class LoginSpec {

  public static RequestSpecification loginRequestSpec = with()
      .log().body()
      .log().uri()
      .log().headers()
      .contentType(ContentType.JSON)
      //.baseUri("https://demoqa.com")
      .basePath("/Account/v1/User");

public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .log(STATUS)
    .log(BODY)
    .build();

  public static ResponseSpecification missingPasswordResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(400)
      .build();

  public static ResponseSpecification existUserResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(406)
      .log(STATUS)
      .log(BODY)
      .build();

  public static ResponseSpecification createResponseSpec = new ResponseSpecBuilder()
      .expectStatusCode(201)
      .log(STATUS)
      .log(BODY)
      .build();

}
