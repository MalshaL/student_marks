// *****************************************************************
//   StudentMarksSystem.java
//
//   This file contains the start-up point for the Student Marks System.
//   To start the system, begin by running this class.
// *****************************************************************


import model.*;
import controller.*;


public class StudentMarksSystem {

  private boolean isLoggedIn = false;
  private User loggedInUser;
  private boolean isExiting = false;

  public void setIsLoggedIn(boolean loggedIn) {
    this.isLoggedIn = loggedIn;
  }

  public boolean isLoggedIn() {
    return this.isLoggedIn;
  }

  public void setLoggedInUser(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }

  public User getLoggedInUser() {
    return this.loggedInUser;
  }

  public void setIsExiting(boolean isExiting) {
    this.isExiting = isExiting;
  }

  public boolean isExiting() {
    return this.isExiting;
  }

  public static void main(String[] args) {
    StudentMarksSystem marksSystem = new StudentMarksSystem();

    while(!marksSystem.isExiting()) {
      if(marksSystem.isLoggedIn()) {
        // display home view for user
        HomeController homeController = new HomeController(marksSystem.getLoggedInUser());
        ResponseObject response = homeController.handle();
        if (response.getMessage().equals(ResponseCode.USER_EXIT)) {
          marksSystem.setIsExiting(true);
        }
      } else {
        // handle the user login function
        LoginController loginController = new LoginController();
        ResponseObject response = loginController.handle();
        if (response.getMessage().equals(ResponseCode.USER_EXIT)) {
          marksSystem.setIsExiting(true);
        }
        if (response.getMessage().equals(ResponseCode.USER_AUTH_SUCCESSFUL)) {
          marksSystem.setLoggedInUser((User) response.getObject());
          marksSystem.setIsLoggedIn(true);
        }
      }

    }
  }
}
