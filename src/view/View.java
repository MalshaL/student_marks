// *****************************************************************
//   View.java
//
//   This file contains the abstract class for views.
// *****************************************************************


package view;

import model.ResponseObject;

public abstract class View {

  // size of the left margin of 10 spaces
  protected static final String MARGIN_LEFT = "%10s";
  // width of the borders of the view
  protected static final int WIDTH = 80;
  // name of the system to display
  protected static final String SYSTEM_NAME = "STUDENT MARKS SYSTEM";
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

  public void printUserChoiceError(ResponseObject response) {
    printCentered(response.getMessage().getMessage(), FontColors.ANSI_RED);
  }

  public void handleUserExit(ResponseObject response) {
    printStarBorder();
    printCentered(response.getMessage().getMessage(), FontColors.ANSI_GREEN);
    printStarBorder();
  }

  protected void printOptions(String[] options) {
    for (int i=0; i<options.length; i++) {
      printOption((i+1)+". "+options[i]);
    }
    String exitOpt = "0. "+EXIT_OPTION;
    printOption(exitOpt);
  }

  private void printOption(String opt) {
    int optLen = WIDTH-opt.length()-2-13;
    System.out.printf(MARGIN_LEFT+"*\t\t"+opt+"%"+optLen+"s*\n", "", "");
  }

  protected void printCenteredHeader(String text) {
    System.out.printf(MARGIN_LEFT+"*", "");
    int leftPadding = (WIDTH-text.length()-2-6)/2;
    for (int i=0; i<leftPadding; i++) {
      System.out.print("-");
    }
    System.out.printf("%3s%S%3s", "", text, "");
    for (int i=0; i<leftPadding; i++) {
      System.out.print("-");
    }
    System.out.print("*\n");
  }

  protected void printCentered(String text, FontColors color) {
    System.out.printf(MARGIN_LEFT+"*", "");

    int len = text.length();
    text = text + (len%2!=0 ? " " : "");
    int leftPadding = (WIDTH-len-2)/2;

    text = color.getValue()+text+FontColors.ANSI_RESET.getValue();
    System.out.printf("%"+leftPadding+"s"+text+"%"+leftPadding+"s", "", "");
    System.out.print("*\n");
  }

  protected void printStarBorder() {
    System.out.printf(MARGIN_LEFT, "");
    for (int i=0; i<WIDTH; i++) {
      System.out.print("*");
    }
    printLineBreak();
  }

  protected void printDashBorder() {
    System.out.printf(MARGIN_LEFT+"*", "");
    for (int i=0; i<WIDTH-2; i++) {
      System.out.print("-");
    }
    System.out.println("*");
  }

  protected void printOnlyBorder() {
    System.out.printf(MARGIN_LEFT+"*%"+(WIDTH-2)+"s*\n", "", "");
  }

  protected void printLineBreak() {
    System.out.println();
  }

  protected void printUserPrompt(String[] options) {
    String prompt = INPUT_PROMPT +"(0-"+options.length+") : ";
    System.out.printf(MARGIN_LEFT+"*\t\t"+prompt, "");
  }

  protected void printInputPrompt(String text) {
    System.out.printf(MARGIN_LEFT+"*\t\t"+text, "");
  }
}
