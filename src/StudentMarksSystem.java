// *****************************************************************
//   StudentMarksSystem.java
//
//   This file contains the start-up point for the Student Marks System.
//   To start the system, begin by running this class.
// *****************************************************************

import java.util.Scanner;
import model.*;
import view.*;


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
    Scanner scanner = new Scanner(System.in);

    while(!marksSystem.isExiting()) {
      if(marksSystem.isLoggedIn()) {
        // display home view for user
      } else {
        // display user login view
        LoginView loginView = new LoginView();
        loginView.displayView();
      }
      int input = scanner.nextInt();
      if (input==0) {
        marksSystem.setIsExiting(true);
      }
    }
  }
}
