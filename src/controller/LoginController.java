// *****************************************************************
//   LoginController.java
//
//   This file contains the controller functions for Login screen.
// *****************************************************************


package controller;

import java.util.*;
import view.*;
import model.*;
import exception.*;


public class LoginController implements Controller {

  private final LoginView loginView;

  public LoginController() {
    loginView = new LoginView();
  }

  public ResponseObject handle() {
    // display the login view to user
    loginView.displayView();
    ResponseObject response = new ResponseObject();

    boolean invalidInput = true;
    while (invalidInput) {
      try {
        // obtain user choice input
        loginView.promptUserChoice();
        response = handleUserChoice();
        invalidInput = false;
      } catch (InvalidInputException e) {
        // handle invalid input
        response.setMessage(ResponseCode.INVALID_INPUT);
        loginView.printUserChoiceError(response);
      }
    }
    return response;
  }

  private ResponseObject handleUserChoice() throws InvalidInputException {
    // get user input
    String input = UserInput.getScanner().nextLine();
    // remove whitespace in input
    input = input.replaceAll("\\s","");
    ResponseObject response;
    try {
      int intValue = Integer.parseInt(input);
      switch(intValue){
        case 0:
          // exit the System
          response = handleSystemExit();
          break;
        case 1:
          // login
          response = handleLogin();
          break;
        default:
          // throw exception for invalid input
          throw new InvalidInputException("Invalid input", null);
        }
    } catch (NumberFormatException e) {
      throw new InvalidInputException("Invalid input", e);
    }
    return response;
  }

  private ResponseObject handleLogin() {
    ResponseObject response = new ResponseObject();
    boolean invalidInput = true;

    while(invalidInput) {
      String[] details = loginView.promptUser();
      if (details[0].equals("") || details[1].equals("")) {
        response.setMessage(ResponseCode.INVALID_INPUT);
        loginView.handleUserLoginStatus(response);
      } else {
        response = validateUser(details);
        if (response.getMessage().equals(ResponseCode.USER_AUTH_FAILED)) {
          loginView.handleUserLoginStatus(response);
        } else {
          // display confirmation in LoginView
          loginView.handleUserLoginStatus(response);
          invalidInput = false;
        }
      }
    }
    // set logged in user details in main method
    return response;
  }

  private ResponseObject handleSystemExit() {
    ResponseObject response = new ResponseObject();
    response.setMessage(ResponseCode.USER_EXIT);
    loginView.handleUserExit(response);
    return response;
  }

  private ResponseObject validateUser(String[] details) {
    // get the user list
    List<User> users = DataController.getInstance().getUserList();
    ResponseObject response = new ResponseObject();

    User authUser = new User(details[0], details[1]);
    User foundUser = findUser(users, authUser);

    if(foundUser != null) {
      response.setMessage(ResponseCode.USER_AUTH_SUCCESSFUL);
      response.setObject(foundUser);
    } else {
      response.setMessage(ResponseCode.USER_AUTH_FAILED);
    }
    return response;
  }

  private User findUser(List<User> users, User authUser) {
    for (User u: users) {
      if (u.getUsername().equals(authUser.getUsername()) &&
          u.getPassword().equals(authUser.getPassword())) {
            return u;
          }
    }
    return null;
  }
}
