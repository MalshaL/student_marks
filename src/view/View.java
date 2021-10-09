// *****************************************************************
//   View.java
//
//   This file contains the abstract class for views.
// *****************************************************************


package view;

import model.ResponseObject;

public abstract class View {

  // width of the borders of the view
  protected static final int WIDTH = 120;
  // name of the system to display
  protected static final String SYSTEM_NAME = "Student Marks System";
  // unique option to exit the system
  protected static final String EXIT_OPTION = "Exit";
  // prompt asking for user's input
  protected static final String INPUT_PROMPT = "Enter your choice ";

  protected enum FontColors {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_RESET("\u001B[0m");

    private final String value;

    FontColors(String s) { this.value = s; }

    protected String getValue() { return this.value; }
  }

  // amount of space to leave on the left side when displaying text
  private int leftPadding;

  View() {
    this.setLeftPadding();
  }

  public void setLeftPadding() {
    // set the default left padding value
    // to the half of total width-length of the system name
    this.leftPadding = (WIDTH- SYSTEM_NAME.length())/2;
  }

  // return the padding value
  public int getLeftPadding() {
    return this.leftPadding;
  }

  public void printUserChoiceError(ResponseObject response) {
    printCentered(FontColors.ANSI_RED.getValue() +
                    response.getMessage().getMessage() + FontColors.ANSI_RESET.getValue(),
            this.getLeftPadding()-10, true);
  }

  public void handleUserExit(ResponseObject response) {
    printCentered(FontColors.ANSI_GREEN.getValue() +
                    response.getMessage().getMessage() + FontColors.ANSI_RESET.getValue(),
            this.getLeftPadding()-10, true);
  }

  protected void printOptions(String[] options) {
    for (int i=0; i<options.length; i++) {
      printCentered((i+1)+". "+options[i], true);
    }
    printLineBreak();
    printCentered("0. "+ EXIT_OPTION, true);
    printLineBreak();
  }

  protected void printCentered(String text, boolean addNewLine) {
    printCentered(text, this.getLeftPadding(), addNewLine);
  }

  protected void printCentered(String text, int leftPadding, boolean addNewLine) {
    for (int i=0; i<leftPadding; i++) {
      System.out.print(" ");
    }
    System.out.print(text);
    if (addNewLine) printLineBreak();
  }

  protected void printStarBorder() {
    for (int i=0; i<WIDTH; i++) {
      System.out.print(FontColors.ANSI_BLUE.getValue() + "*");
    }
    System.out.print(FontColors.ANSI_RESET.getValue() + "\n");
  }

  protected void printCenteredLineBorder(String text) {
    int length = text.length();
    for (int i=0; i<leftPadding-5; i++) {
      System.out.print(" ");
    }
    for (int i=0; i<length+10; i++) {
      System.out.print("-");
    }
    System.out.print("\n");
  }

  protected void printLineBreak() {
    System.out.println("");
  }

  protected void printUserPrompt(String[] options) {
      printCentered(INPUT_PROMPT +"(0-"+options.length+") :",
              this.getLeftPadding()-5, false);
  }
}
