package specs;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.matcher.DetailedCookieMatcher;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.Argument;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseLogSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.hamcrest.Matcher;

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

}
