// *****************************************************************
//   View.java
//
//   This file contains the abstract class for views.
// *****************************************************************


package view;

import model.*;

public abstract class View {

  // width of the borders of the view
  protected static final int WIDTH = 80;
  // name of the system to display
  protected static final String SYSTEMNAME = "Student Marks System";
  // unique option to exit the system
  protected static final String EXITOPTION = "Exit";
  // prompt asking for user's input
  protected static final String INPUTPROMPT = "Enter your choice ";

  // amount of space to leave on the left side when displaying text
  private int leftPadding;

  public void setLeftPadding() {
    // set the default left padding value
    // to the half of total width-length of the system name
    this.leftPadding = (WIDTH-SYSTEMNAME.length())/2;
  }

  // return the padding value
  public int getLeftPadding() {
    return this.leftPadding;
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

  protected void printBorder() {
    for (int i=0; i<WIDTH; i++) {
      System.out.print("*");
    }
    System.out.print("\n");
  }

  protected void printLineBreak() {
    System.out.println("");
  }
}
