// *****************************************************************
//   LecturerHomeView.java
//
//   This file contains the view for Lecturer Home screen.
// *****************************************************************


package view;

import model.EnrolledUnit;
import model.TeachingUnit;
import model.Unit;

import java.util.List;

public class LecturerHomeView extends View {

    private static final String[] OPTIONS = {"View My Units", "Add Student Grades",
            "View Student Grades", "Update Student Grades"};

    public void displayView() {
        printStarBorder();
        printCenteredHeader("home");
        printCentered("Logged in as Lecturer", FontColors.ANSI_RESET);
        printStarBorder();
        printOptions(OPTIONS, EXIT_OPTION);
    }

    public void promptUserChoice() {
        printUserPrompt(OPTIONS.length);
    }

    public void promptUserChoice(int optionCount) {
        printUserPrompt(optionCount);
    }

    public void displayUnitData(List<TeachingUnit> unitList, List<String> unitNames) {
        printStarBorder();
        printCentered("My Units", FontColors.ANSI_RESET);
        printOnlyBorder();
        if (unitList.isEmpty()) {
            printCentered("No units have been currently allocated", FontColors.ANSI_RESET);
        } else {
            System.out.printf(MARGIN_LEFT+"* %-12s%-12s%-12s%"+(WIDTH-36-4)+"s *\n",
                    "", "UNIT ID", "COURSE ID", "SEMESTER ID", "UNIT NAME");
            printDashBorder();
            for (int i=0; i<unitList.size(); i++) {
                System.out.printf(MARGIN_LEFT+"* %-12s%-12s%-12s%"+(WIDTH-36-4)+"s *\n",
                        "", unitList.get(i).getUnitId(), unitList.get(i).getCourseId(),
                        unitList.get(i).getSemesterId(), unitNames.get(i));
            }
        }
        printOnlyBorder();
        printCentered("Found "+unitList.size()+" units.", FontColors.ANSI_RESET);
        printCentered("Press Enter to return to Home screen: ", FontColors.ANSI_RESET);
    }

    public void displayUnitSelection(List<TeachingUnit> unitList, List<String> unitNames) {
        printStarBorder();
        printCentered("View grades", FontColors.ANSI_RESET);
        printCentered("Select unit to view grades", FontColors.ANSI_RESET);
        if (unitList.isEmpty()) {
            printCentered("No units have been currently allocated", FontColors.ANSI_RESET);
        } else {
            // create options list with unit details
            // user will select a unit to view grades
            String[] unitOptions = new String[unitList.size()];
            printDashBorder();
            for (int i=0; i<unitList.size(); i++) {
                unitOptions[i] = unitList.get(i).getUnitId() + " - " +
                        unitNames.get(i) + " - " +
                        unitList.get(i).getSemesterId();
            }
            printOptions(unitOptions, BACK_OPTION);
        }
        printOnlyBorder();
    }

    public void displayUnitGrades(List<EnrolledUnit> unitList, String unitName, String unitId, double avg) {
        printStarBorder();
        printCentered("Grades - "+unitId+" "+unitName, FontColors.ANSI_RESET);
        printOnlyBorder();
        if (unitList.isEmpty()) {
            printCentered("No unit enrollments found", FontColors.ANSI_RESET);
        } else {
            System.out.printf(MARGIN_LEFT+"* %-18s%-18s%-18s%"+(WIDTH-54-4)+"s *\n",
                    "", "STUDENT ID", "SEMESTER ID", "MARK", "GRADE");
            printDashBorder();
            for (EnrolledUnit enrolledUnit : unitList) {
                System.out.printf(MARGIN_LEFT + "* %-18s%-18s%-18s%" + (WIDTH -54- 4) + "s *\n",
                        "", enrolledUnit.getStudentId(), enrolledUnit.getSemesterId(),
                        enrolledUnit.getMark(), enrolledUnit.getGrade());
            }
        }
        printOnlyBorder();
        printCentered("Average Mark for Unit: "+String.format("%.2f", avg), FontColors.ANSI_RESET);
        printOnlyBorder();
        printCentered("Press Enter to return to Home screen: ", FontColors.ANSI_RESET);
    }
}
