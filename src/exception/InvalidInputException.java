// *****************************************************************
//   InvalidInputException.java
//
//   This file defines the exception for invalid user inputs.
// *****************************************************************


package exception;


public class InvalidInputException extends Exception{

  public InvalidInputException (String message, Throwable error) {
    super(message, error);
  }
}
