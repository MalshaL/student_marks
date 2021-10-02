// *****************************************************************
//   LoginView.java
//
//   This file contains the view for Login screen.
// *****************************************************************


package view;


public class LoginView {
  private static final int WIDTH = 80;

  public void displayView() {
    printBorder();
    printCentered("Student Marks System");
    printBorder();
  }

  private void printBorder() {
    for (int i=0; i<WIDTH; i++) {
      System.out.print("*");
    }
    System.out.print("\n");
  }

  private void printCentered(String text) {
    int len = text.length();
    int padding = (WIDTH-len)/2;
    for (int i=0; i<padding; i++) {
      System.out.print(" ");
    }
    System.out.println(text);
  }

  private void printOptions() {
    
  }
}
