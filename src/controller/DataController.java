// *****************************************************************
//   DataController.java
//
//   This file contains the functions to handle filedata manipulation.
// *****************************************************************


package controller;

import java.util.*;
import view.*;
import model.*;
import exception.*;


public class DataController implements Controller {

  private static final DataController dataController = new DataController();
  List<User> userList;
  List<Unit> unitList;
  List<TeachingUnit> teachingUnits;
  List<Lecturer> lecturerList;

  private DataController() {
    userList = new ArrayList<>();
    unitList = new ArrayList<>();
    teachingUnits = new ArrayList<>();
    lecturerList = new ArrayList<>();
    this.setUserList();
    this.setUnitList();
    this.setLecturerList();
    this.setTeachingUnits();
  }

  public static DataController getInstance() {
    return dataController;
  }

  public ResponseObject handle() {
    return null;
  }

  public List<User> getUserList() {
    return this.userList;
  }

  public void setUserList() {
    User user1 = new User("1", "admin", "admin", User.UserLevel.ADMIN);
    User user2 = new User("2", "student", "student", User.UserLevel.STUDENT);
    User user3 = new User("12001", "John", "john", User.UserLevel.LECTURER);
    User user4 = new User("12002", "Anne", "anne", User.UserLevel.LECTURER);
    userList.add(user1);
    userList.add(user2);
    userList.add(user3);
    userList.add(user4);
  }

  public List<Unit> getUnitList() {
    return unitList;
  }

  public void setUnitList() {
    Unit unit1 = new Unit("COMP1001", "Programming in Java");
    Unit unit2 = new Unit("COMP2001", "Object Oriented Programming in Java");
    Unit unit3 = new Unit("COMP2002", "Functional Programming");
    Unit unit4 = new Unit("COMP1002", "Programming in Python");
    unitList.add(unit1);
    unitList.add(unit2);
    unitList.add(unit3);
    unitList.add(unit4);
  }

  public List<TeachingUnit> getTeachingUnits() {
    return teachingUnits;
  }

  public void setTeachingUnits() {
    TeachingUnit unit1 = new TeachingUnit("COMP1001", "ENG1001", "AU2021", "12001");
    TeachingUnit unit2 = new TeachingUnit("COMP2001", "ENG2001", "SP2021", "12001");
    TeachingUnit unit3 = new TeachingUnit("COMP2002", "ENG2001", "AU2021", "12002");
    TeachingUnit unit4 = new TeachingUnit("COMP1002", "ENG1001", "SP2021", "12002");
    teachingUnits.add(unit1);
    teachingUnits.add(unit2);
    teachingUnits.add(unit3);
    teachingUnits.add(unit4);
  }

  public List<Lecturer> getLecturerList() {
    return lecturerList;
  }

  public void setLecturerList() {
    Lecturer lecturer1 = new Lecturer("12001", "John", "Doe");
    Lecturer lecturer2 = new Lecturer("12002", "Anne", "Cooper");
    Lecturer lecturer3 = new Lecturer("12003", "Jane", "Bing");
    Lecturer lecturer4 = new Lecturer("12004", "Peter", "Milburn");
    lecturerList.add(lecturer1);
    lecturerList.add(lecturer2);
    lecturerList.add(lecturer3);
    lecturerList.add(lecturer4);
  }
}
