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
    printBorder();
    printCentered(SYSTEMNAME, true);
    printBorder();
    printLineBreak();
    printOptions();
  }

  public String[] promptUser() {
    printLineBreak();
    printCentered("Logging in", true);
    printBorder();
    printCentered("Enter username: ", this.getLeftPadding()-5, false);
    String username = UserInput.getScanner().nextLine();
    printCentered("Enter password: ", this.getLeftPadding()-5, false);
    String password = UserInput.getScanner().nextLine();
    if (username.equals("") || password.equals("")) {

    }
    return new String[]{username, password};
  }

  public void handleUserChoiceError(ResponseObject response) {
    printCentered(response.getMessage().getMessage(), this.getLeftPadding()-5, true);
  }

  public void handleUserLoginError() {

  }

  private void printOptions() {
    for (int i=0; i<OPTIONS.length; i++) {
      printCentered((i+1)+". "+OPTIONS[i], true);
    }
    printLineBreak();
    printCentered("0. "+EXITOPTION, true);
    printLineBreak();
    printCentered(INPUTPROMPT+"(0-"+OPTIONS.length+") :",
        this.getLeftPadding()-5, false);
  }

}
