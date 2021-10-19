// *****************************************************************
//   Unit.java
//
//   This file contains the model class for Unit.
// *****************************************************************


package model;


import controller.FileController;

public class Unit {

    private String unitId;
    private String unitName;
    private int credits;

    public Unit(String unitId, String unitName, int credits) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.credits = credits;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getCsvRow() {
        return this.getUnitId()+ FileController.CELL_SEPARATOR +
                this.getUnitName()+ FileController.CELL_SEPARATOR +
                this.getCredits();
    }
}