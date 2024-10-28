package models.lombok;

import java.util.Arrays;
import lombok.Data;

@Data
public class CreateUserResponseModel {

  String userID, username;
  String [] books;

}
