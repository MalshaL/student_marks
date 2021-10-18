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
    // boolean variable to indicate if the semester is currently in session
    private boolean current;

    public Semester(String semesterId, String semesterName, boolean current) {
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.current = current;
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

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public String getCsvRow() {
        return this.getSemesterId() + FileController.CELL_SEPARATOR +
                this.getSemesterName() + FileController.CELL_SEPARATOR +
                this.isCurrent();
    }
}