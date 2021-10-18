// *****************************************************************
//   Enrolled Unit.java
//
//   This file contains the model class for a Unit enrolled by a student.
// *****************************************************************


package model;

import controller.FileController;

public class EnrolledUnit {

    private String unitId;
    private String studentId;
    private String courseId;
    private String semesterId;
    private double mark;
    private Grade grade;

    public EnrolledUnit(String unitId, String studentId, String courseId, String semesterId) {
        this.unitId = unitId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.mark = 0;
        this.grade = Grade.NA;
    }

    public EnrolledUnit(String unitId, String studentId, String courseId, String semesterId, double mark, Grade grade) {
        this.unitId = unitId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.mark = mark;
        this.grade = grade;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getCsvRow() {
        return this.getUnitId() + FileController.CELL_SEPARATOR +
                this.getStudentId() + FileController.CELL_SEPARATOR +
                this.getCourseId() + FileController.CELL_SEPARATOR +
                this.getSemesterId() + FileController.CELL_SEPARATOR +
                this.getMark() + FileController.CELL_SEPARATOR +
                this.getGrade().toString();
    }
}