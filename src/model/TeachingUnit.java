// *****************************************************************
//   TeachingUnit.java
//
//   This file contains the model class for Unit taught in a specific
//   time by a specific lecturer.
// *****************************************************************


package model;


public class TeachingUnit {

    private String unitId;
    private String courseId;
    private String semesterId;
    private String lecturerId;

    public TeachingUnit(String unitId, String courseId, String semesterId, String lecturerId) {
        this.unitId = unitId;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.lecturerId = lecturerId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
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

    public String getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}
