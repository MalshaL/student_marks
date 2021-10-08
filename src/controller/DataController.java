// *****************************************************************
//   DataController.java
//
//   This file contains the functions to handle filedata manipulation.
// *****************************************************************


package controller;

import java.util.*;
import view.*;
import model.*;
import exception.*;


public class DataController implements Controller {

  private static final DataController dataController = new DataController();
  List<User> userList;

  private DataController() {
    userList = new ArrayList<>();
    this.setUserList();
  }

  public static DataController getInstance() {
    return dataController;
  }

  public ResponseObject handle() {
    return null;
  }

  public List<User> getUserList() {
    return this.userList;
  }

  public void setUserList() {
    User user1 = new User(1, "admin", "admin", User.UserLevel.ADMIN);
    User user2 = new User(2, "student", "student", User.UserLevel.STUDENT);
    userList.add(user1);
    userList.add(user2);
  }
}
