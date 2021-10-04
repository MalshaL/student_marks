// *****************************************************************
//   ResponseObject.java
//
//   This file contains the model class for responses.
// *****************************************************************


package model;


public class ResponseObject {

  private ResponseCode message;
  private Object object;

  public ResponseObject() {
    // empty constructor
  }

  public ResponseObject(ResponseCode message) {
    this.message = message;
  }

  public ResponseObject(ResponseCode message, Object object) {
    this.message = message;
    this.object = object;
  }

  public void setMessage(ResponseCode message) {
    this.message = message;
  }

  public ResponseCode getMessage() {
    return this.message;
  }

  public void setObject(Object object) {
    this.object = object;
  }

  public Object getObject() {
    return this.object;
  }
}
