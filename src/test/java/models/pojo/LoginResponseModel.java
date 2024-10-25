package models.pojo;

public class LoginResponseModel {

  String UserName, userID;

  public void setUserName(String userName) {
    UserName = userName;
  }

  public void setPassword(String password) {
    userID = password;
  }

  public String getUserName() {
    return UserName;
  }
  public String getPassword() {
    return userID;
  }
}
