// *****************************************************************
//   LoginView.java
//
//   This file contains the view for Login screen.
// *****************************************************************


package view;

import model.*;


public class LoginView extends View {

  // options in the login menu
  private static final String[] OPTIONS = {"Log In"};

  // constructor
  public LoginView() {
    // set the default left padding value
    this.setLeftPadding();
  }

  public void displayView() {
    printLineBreak();
    printStarBorder();
    printCentered(SYSTEM_NAME, true);
    printStarBorder();
    printLineBreak();
    printOptions();
  }

  public void promptUserChoice() {
    printCentered(INPUT_PROMPT +"(0-"+OPTIONS.length+") :",
            this.getLeftPadding()-5, false);
  }

  public void handleUserChoiceError(ResponseObject response) {
    printCentered(FontColors.ANSI_RED.getValue() +
                    response.getMessage().getMessage() + FontColors.ANSI_RESET.getValue(),
            this.getLeftPadding()-10, true);
  }

  public String[] promptUser() {
    printLineBreak();
    printCentered("Logging in", true);
    printCenteredLineBorder("Logging in");
    printCentered("Enter username: ", this.getLeftPadding()-5, false);
    String username = UserInput.getScanner().nextLine();
    printCentered("Enter password: ", this.getLeftPadding()-5, false);
    String password = UserInput.getScanner().nextLine();
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

  public void handleUserExit(ResponseObject response) {
    printCentered(FontColors.ANSI_GREEN.getValue() +
                    response.getMessage().getMessage() + FontColors.ANSI_RESET.getValue(),
            this.getLeftPadding()-10, true);
  }

  private void printOptions() {
    for (int i=0; i<OPTIONS.length; i++) {
      printCentered((i+1)+". "+OPTIONS[i], true);
    }
    printLineBreak();
    printCentered("0. "+ EXIT_OPTION, true);
    printLineBreak();
  }

}
