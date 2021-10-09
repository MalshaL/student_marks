// *****************************************************************
//   Unit.java
//
//   This file contains the model class for Unit.
// *****************************************************************


package model;


import controller.DataController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Unit {

    private String unitId;
    private String unitName;

    public Unit(String unitId, String unitName) {
        this.unitId = unitId;
        this.unitName = unitName;
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
}