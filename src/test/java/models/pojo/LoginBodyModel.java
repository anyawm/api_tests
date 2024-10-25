package models.pojo;

public class LoginBodyModel {

  String UserName, Password;

  public void setUserName(String userName) {
    UserName = userName;
  }

  public void setPassword(String password) {
    Password = password;
  }

  public String getUserName() {
    return UserName;
  }
  public String getPassword() {
    return Password;
  }


}
