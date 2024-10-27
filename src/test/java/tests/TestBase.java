package tests;

import com.github.javafaker.Faker;

public class TestBase {

  Faker faker = new Faker();
  String userNameGenerate = faker.name().firstName();

}
