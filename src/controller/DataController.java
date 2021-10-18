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
  List<Person> lecturerList;
  List<Person> studentList;
  List<EnrolledUnit> enrolledUnits;
  List<Course> courseList;
  List<Semester> semesterList;

  private DataController() {
    userList = new ArrayList<>();
    unitList = new ArrayList<>();
    teachingUnits = new ArrayList<>();
    lecturerList = new ArrayList<>();
    studentList = new ArrayList<>();
    enrolledUnits = new ArrayList<>();
    courseList = new ArrayList<>();
    this.semesterList = new ArrayList<>();
    this.setUserList();
    this.setUnitList();
    this.setLecturerList();
    this.setStudentList();
    this.setTeachingUnits();
    this.setEnrolledUnits();
    this.setCourseList();
    this.setSemesterList();
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
    this.userList = FileController.getInstance().readUserData();
  }

  public List<Unit> getUnitList() {
    return unitList;
  }

  public void setUnitList() {
    this.unitList = FileController.getInstance().readUnitData();
  }

  public List<TeachingUnit> getTeachingUnits() {
    return teachingUnits;
  }

  public void setTeachingUnits() {
    this.teachingUnits = FileController.getInstance().readTeachingUnitData();
  }

  public List<Person> getLecturerList() {
    return lecturerList;
  }

  public void setLecturerList() {
    this.lecturerList = FileController.getInstance().readPersonData(User.UserLevel.LECTURER);
  }

  public List<Person> getStudentList() {
    return studentList;
  }

  public void setStudentList() {
    this.studentList = FileController.getInstance().readPersonData(User.UserLevel.STUDENT);
  }

  public List<EnrolledUnit> getEnrolledUnits() {
    return enrolledUnits;
  }

  public void setEnrolledUnits() {
    this.enrolledUnits = FileController.getInstance().readEnrolledUnitData();
  }

  public List<Course> getCourseList() {
    return courseList;
  }

  public void setCourseList() {
    FileController.getInstance().readCourseData();
  }

  public List<Semester> getSemesterList() {
    return semesterList;
  }

  public void setSemesterList() {
    FileController.getInstance().readSemesterData();
  }
}
