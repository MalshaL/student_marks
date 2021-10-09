// *****************************************************************
//   User.java
//
//   This file contains the model class for User.
// *****************************************************************


package model;


public class User {

  public enum UserLevel {
    STUDENT,
    LECTURER,
    ADMIN
  }

  private String userId;
  private String username;
  private String password;
  private UserLevel userLevel;

  public User(String id, String username, String pwd, UserLevel userLevel) {
    this.userId = id;
    this.username = username;
    this.password = pwd;
    this.userLevel = userLevel;
  }

  public User(String username, String pwd) {
    this.username = username;
    this.password = pwd;
  }

  public void setUserId(String id) {
    this.userId = id;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public void setPassword(String pwd) {
    this.password = pwd;
  }

  public String getPassword() {
    return this.password;
  }

  public void setUserLevel(UserLevel userLevel) {
    this.userLevel = userLevel;
  }

  public UserLevel getUserLevel() {
    return this.userLevel;
  }

}
