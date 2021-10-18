// *****************************************************************
//   DataController.java
//
//   This file contains the functions to handle filedata manipulation.
// *****************************************************************


package controller;

import java.io.IOException;
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
    semesterList = new ArrayList<>();
  }

  public static DataController getInstance() {
    return dataController;
  }

  public ResponseObject handle() {
    ResponseObject response = new ResponseObject();
    try {
      // read all data from files
      this.setUserList(FileController.getInstance().readUserData());
      this.setUnitList(FileController.getInstance().readUnitData());
      this.setLecturerList(FileController.getInstance().readPersonData(User.UserLevel.LECTURER));
      this.setStudentList(FileController.getInstance().readPersonData(User.UserLevel.STUDENT));
      this.setTeachingUnits(FileController.getInstance().readTeachingUnitData());
      this.setEnrolledUnits(FileController.getInstance().readEnrolledUnitData());
      this.setCourseList(FileController.getInstance().readCourseData());
      this.setSemesterList(FileController.getInstance().readSemesterData());
      response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
    } catch (IOException e) {
      response.setMessage(ResponseCode.DATA_QUERY_FAILED);
    }
    return response;
  }

  public ResponseObject writeAllData() {
    ResponseObject response = new ResponseObject();
    try {
      FileController.getInstance().writeUserData(this.getUserList(), false);
      FileController.getInstance().writeUnitData(this.getUnitList(), false);
      FileController.getInstance().writePersonData(this.getLecturerList(), false, User.UserLevel.LECTURER);
      FileController.getInstance().writePersonData(this.getStudentList(), false, User.UserLevel.STUDENT);
      FileController.getInstance().writeTeachingUnitData(this.getTeachingUnits(), false);
      FileController.getInstance().writeEnrolledUnitData(this.getEnrolledUnits(), false);
      FileController.getInstance().writeCourseData(this.getCourseList(), false);
      FileController.getInstance().writeSemesterData(this.getSemesterList(), false);
      response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
    } catch (IOException e) {
      response.setMessage(ResponseCode.DATA_QUERY_FAILED);
    }
    return response;
  }

  public List<User> getUserList() {
    return userList;
  }

  public void setUserList(List<User> userList) {
    this.userList = userList;
  }

  public List<Unit> getUnitList() {
    return unitList;
  }

  public void setUnitList(List<Unit> unitList) {
    this.unitList = unitList;
  }

  public List<TeachingUnit> getTeachingUnits() {
    return teachingUnits;
  }

  public void setTeachingUnits(List<TeachingUnit> teachingUnits) {
    this.teachingUnits = teachingUnits;
  }

  public List<Person> getLecturerList() {
    return lecturerList;
  }

  public void setLecturerList(List<Person> lecturerList) {
    this.lecturerList = lecturerList;
  }

  public List<Person> getStudentList() {
    return studentList;
  }

  public void setStudentList(List<Person> studentList) {
    this.studentList = studentList;
  }

  public List<EnrolledUnit> getEnrolledUnits() {
    return enrolledUnits;
  }

  public void setEnrolledUnits(List<EnrolledUnit> enrolledUnits) {
    this.enrolledUnits = enrolledUnits;
  }

  public List<Course> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<Course> courseList) {
    this.courseList = courseList;
  }

  public List<Semester> getSemesterList() {
    return semesterList;
  }

  public void setSemesterList(List<Semester> semesterList) {
    this.semesterList = semesterList;
  }
}
