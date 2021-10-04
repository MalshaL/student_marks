// *****************************************************************
//   UserInput.java
//
//   This file contains the model class for the Scanner object
//   used to obtain user input.
//   The Scanner object is created once per application run
//   and is accessed as a singleton.
// *****************************************************************

package model;

import java.util.Scanner;


public class UserInput {

  private static Scanner scanner = new Scanner(System.in);

  private UserInput() {
    // make the constructor private so that the class cannot be instantiated
  }

  // access the static Scanner object
  public static Scanner getScanner() {
    return scanner;
  }

}
