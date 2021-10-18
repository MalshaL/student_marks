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
    // write all application data to files before exiting the system
    writeAppData();
    this.isExiting = isExiting;
  }

  public boolean isExiting() {
    return this.isExiting;
  }

  private void writeAppData() {
    ResponseObject response = DataController.getInstance().writeAllData();
    if (response.getMessage().equals(ResponseCode.DATA_QUERY_FAILED)) {
      System.out.println("Writing application data to files has failed.\n" +
              "Your changes may not be saved!\n" +
              "Please verify that all data files are available in the 'data' directory" +
              "\n\nExiting the System.");
    }
  }

  public static void main(String[] args) {
    StudentMarksSystem marksSystem = new StudentMarksSystem();

    while(!marksSystem.isExiting()) {
      // if the user is logged in
      if(marksSystem.isLoggedIn()) {
        // display home view for user
        HomeController homeController = new HomeController(marksSystem.getLoggedInUser());
        ResponseObject response = homeController.handle();
        // user has chosen to exit the system
        if (response.getMessage().equals(ResponseCode.USER_EXIT)) {
          marksSystem.setIsExiting(true);
        }
      } else {
        // if the user has not logged in (application is starting up)
        // call DataController handle to run data load at application start
        ResponseObject dataResponse = DataController.getInstance().handle();
        if(dataResponse.getMessage().equals(ResponseCode.DATA_QUERY_FAILED)) {
          System.out.println("Error in reading data from files\nPlease verify that all data " +
                  "files are available in the 'data' directory\n\nExiting the System.");
          marksSystem.setIsExiting(true);
        } else {
          // handle the user login function
          LoginController loginController = new LoginController();
          ResponseObject response = loginController.handle();
          // user chose to exit the system
          if (response.getMessage().equals(ResponseCode.USER_EXIT)) {
            marksSystem.setIsExiting(true);
          }
          // user has successfully logged into the system
          if (response.getMessage().equals(ResponseCode.USER_AUTH_SUCCESSFUL)) {
            // set the logged in user details
            marksSystem.setLoggedInUser((User) response.getObject());
            marksSystem.setIsLoggedIn(true);
          }
        }
      }
    }
  }
}
