// *****************************************************************
//   Grade.java
//
//   This file contains the enum for Grade.
// *****************************************************************


package model;

public enum Grade {
    HIGH_DISTINCTION("HD", 85, 100, 7),
    DISTINCTION("D", 75, 84, 6),
    CREDIT("C", 65, 74, 5),
    PASS("P", 50, 64, 4),
    FAIL("F", 0, 49, 0);

    private final String notation;
    private final double lowerBound;
    private final double higherBound;
    private final int gradeValue;

    Grade(String name, double lowerBound, double higherBound, int gradeValue) {
        this.notation = name;
        this.lowerBound = lowerBound;
        this.higherBound = higherBound;
        this.gradeValue = gradeValue;
    }

    public String getNotation() {
        return notation;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getHigherBound() {
        return higherBound;
    }

    public int getGradeValue() {
        return gradeValue;
    }
}