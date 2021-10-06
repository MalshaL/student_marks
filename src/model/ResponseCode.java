// *****************************************************************
//   ResponseCode.java
//
//   This file contains the model class for response codes.
// *****************************************************************


package model;

public enum ResponseCode {
  USER_EXIT("User exiting the system."),
  USER_AUTH_SUCCESSFUL("User authentication is successful."),
  USER_AUTH_FAILED("User authentication failed."),
  INVALID_INPUT("Input is invalid. Please try again.");

  private final String message;

  ResponseCode(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
