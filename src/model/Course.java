// *****************************************************************
//   Course.java
//
//   This file contains the model class for Courses taken by students.
// *****************************************************************


package model;

import controller.FileController;

public class Course {

    private String courseId;
    private String courseName;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCsvRow() {
        return this.getCourseId() + FileController.CELL_SEPARATOR +
                this.getCourseName();
    }
}