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
    printCentered(FontColors.ANSI_BLUE.getValue() + SYSTEM_NAME + FontColors.ANSI_RESET.getValue(),
            true);
    printStarBorder();
    printLineBreak();
    printOptions(OPTIONS);
  }

  public void promptUserChoice() {
    printUserPrompt(OPTIONS);
  }

  public String[] promptUser() {
    printLineBreak();
    printCentered("Logging in", true);
    printCenteredLineBorder("Logging in");
    printCentered("Enter username: ", this.getLeftPadding()-5, false);
    String username = UserInput.getScanner().nextLine();
    printCentered("Enter password: ", this.getLeftPadding()-5, false);
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
    if (response.getMessage().equals(ResponseCode.USER_AUTH_SUCCESSFUL)){
      printCentered( FontColors.ANSI_GREEN.getValue() +
                      response.getMessage().getMessage() + FontColors.ANSI_RESET.getValue(),
              this.getLeftPadding()-10, true);
    } else {
      printCentered(FontColors.ANSI_RED.getValue() +
                      response.getMessage().getMessage() + FontColors.ANSI_RESET.getValue(),
              this.getLeftPadding() - 10, true);
    }
  }



}
