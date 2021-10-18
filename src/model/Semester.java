// *****************************************************************
//   Semester.java
//
//   This file contains the model class for Semester.
// *****************************************************************


package model;


import controller.FileController;

public class Semester {

    private String semesterId;
    private String semesterName;

    public Semester(String semesterId, String semesterName) {
        this.semesterId = semesterId;
        this.semesterName = semesterName;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getCsvRow() {
        return this.getSemesterId() + FileController.CELL_SEPARATOR +
                this.getSemesterName();
    }
}