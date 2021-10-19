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
  // set the size of the tab to 12 spaces
  protected static final int TAB = 12;
  // width of the borders of the view
  protected static final int WIDTH = 80;
  // name of the system to display
  protected static final String SYSTEM_NAME = "STUDENT MARKS SYSTEM";
  // unique option to exit the system
  protected static final String EXIT_OPTION = "Exit";
  // unique option to go to the previous screen
  protected static final String BACK_OPTION = "Back";
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
    // print error of user input
    printCentered(response.getMessage().getMessage(), FontColors.ANSI_RED);
  }

  public void printUserChoiceInfo(ResponseObject response) {
    // print information for user
    printCentered(response.getMessage().getMessage(), FontColors.ANSI_BLUE);
    printOnlyBorder();
  }

  public void handleUserExit(ResponseObject response) {
    // print exiting message
    printStarBorder();
    printCentered(response.getMessage().getMessage(), FontColors.ANSI_GREEN);
    printStarBorder();
  }

  protected void printOptions(String[] options, String exitOption) {
    // print array of options with numerical input index (e.g.: 0, 1, 2..)
    for (int i=0; i<options.length; i++) {
      printOption((i+1)+". "+options[i]);
    }
    String exitOpt = "0. "+exitOption;
    printOption(exitOpt);
  }

  protected void printCustomOptions(String[] options) {
    // print option lists that do not use numerical input index (e.g.: [, ]..)
    for (int i=0; i<options.length; i++) {
      printOption(options[i]);
    }
    String exitOpt = "0 - "+ BACK_OPTION;
    printOption(exitOpt);
  }

  private void printOption(String opt) {
    // print each option
    int optLen = WIDTH-opt.length()-2-TAB;
    System.out.printf(MARGIN_LEFT+"*%"+TAB+"s"+opt+"%"+optLen+"s*\n", "", "", "");
  }

  protected void printCenteredHeader(String text) {
    // print a line of text centered in the view
    System.out.printf(MARGIN_LEFT+"*", "");
    // calculate space to leave on left
    int leftPadding = (WIDTH-text.length()-2-6)/2;
    // print dashed line on left
    for (int i=0; i<leftPadding; i++) {
      System.out.print("-");
    }
    // print the line of text in the middle
    System.out.printf("%3s%S%3s", "", text, "");
    // print dashed line on right
    for (int i=0; i<leftPadding; i++) {
      System.out.print("-");
    }
    System.out.print("*\n");
  }

  protected void printCentered(String text, FontColors color) {
    // print line of text without dashed lines using a given color
    System.out.printf(MARGIN_LEFT+"*", "");
    // check if text length is odd or even
    int len = text.length();
    // add a space if it's odd
    text = text + (len%2!=0 ? " " : "");
    int leftPadding = (WIDTH-len-2)/2;
    // reset font color if a different color was used
    text = color.getValue()+text+FontColors.ANSI_RESET.getValue();
    // print centered text
    System.out.printf("%"+leftPadding+"s"+text+"%"+leftPadding+"s", "", "");
    System.out.print("*\n");
  }

  protected void printStarBorder() {
    // print a border of stars
    System.out.printf(MARGIN_LEFT, "");
    for (int i=0; i<WIDTH; i++) {
      System.out.print("*");
    }
    printLineBreak();
  }

  protected void printDashBorder() {
    // print a border of dashes
    System.out.printf(MARGIN_LEFT+"*", "");
    for (int i=0; i<WIDTH-2; i++) {
      System.out.print("-");
    }
    System.out.println("*");
  }

  protected void printOnlyBorder() {
    // print only the stars for left and right border
    System.out.printf(MARGIN_LEFT+"*%"+(WIDTH-2)+"s*\n", "", "");
  }

  protected void printLineBreak() {
    System.out.println();
  }

  protected void printUserPrompt(int optionCount) {
    // print prompt for user to enter input when the option count is known
    String prompt = INPUT_PROMPT +"(0-"+optionCount+") : ";
    System.out.printf(MARGIN_LEFT+"*%"+TAB+"s"+prompt, "", "");
  }

  protected void printInputPrompt(String text) {
    // print custom input prompt
    System.out.printf(MARGIN_LEFT+"*%"+TAB+"s"+text, "", "");
  }
}
