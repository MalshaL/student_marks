// *****************************************************************
//   LoginView.java
//
//   This file contains the view for Login screen.
// *****************************************************************


package view;


import java.io.Console;
import model.*;


public class LoginView extends View {

  // options in the login menu
  private static final String[] OPTIONS = {"Log In"};

  // constructor
  public LoginView() {

  }

  public void displayView() {
    printLineBreak();
    printStarBorder();
    printOnlyBorder();
    printCenteredHeader(SYSTEM_NAME);
    printOnlyBorder();
    printStarBorder();
    printOnlyBorder();
    printOptions(OPTIONS);
  }

  public void promptUserChoice() {
    printUserPrompt(OPTIONS);
  }

  public String[] promptUser() {
    printStarBorder();
    printCenteredHeader("Log in");
    printStarBorder();
    printInputPrompt("Enter username: ");
    String username = UserInput.getScanner().nextLine();
    printInputPrompt("Enter password: ");
    Console console = System.console();
    String password;
    if (console == null) {
      password = UserInput.getScanner().nextLine();
    } else {
      char[] pwd = console.readPassword();
      password = new String(pwd);
    }
    return new String[]{username, password};
  }

  public void handleUserLoginStatus(ResponseObject response) {
    if (response.getMessage().equals(ResponseCode.USER_AUTH_SUCCESSFUL)) {
      printCentered(response.getMessage().getMessage(), FontColors.ANSI_GREEN);
    } else {
      printCentered(response.getMessage().getMessage(), FontColors.ANSI_RED);
    }
  }



}
