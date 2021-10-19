// *****************************************************************
//   LecturerHomeView.java
//
//   This file contains the view for Lecturer Home screen.
// *****************************************************************


package view;

import model.EnrolledUnit;
import model.TeachingUnit;
import model.Person;
import model.Unit;

import java.util.ArrayList;
import java.util.List;


public class LecturerHomeView extends View {

    // list of options for the main menu
    private static final String[] OPTIONS = {"View My Units",
            "View Unit Grades", "View Student Grades", "Add/Update Student Grades"};

    public void displayView() {
        // print the main menu
        printStarBorder();
        printCenteredHeader("home");
        printCentered("Logged in as Lecturer", FontColors.ANSI_RESET);
        printStarBorder();
        printOptions(OPTIONS, EXIT_OPTION);
    }

    public void promptUserChoice() {
        // prompt for user input for main menu
        printUserPrompt(OPTIONS.length);
    }

    public void promptUserChoice(int optionCount) {
        // prompt for user input for custom menu
        printUserPrompt(optionCount);
    }

    public void promptUserChoice(String text) {
        // prompt for user input for custom prompt
        printInputPrompt(text);
    }

    public void displayUnitData(List<TeachingUnit> unitList, List<String> unitNames) {
        // print units taught by the lecturer
        printStarBorder();
        printCentered("My Units", FontColors.ANSI_RESET);
        printOnlyBorder();
        // if unit list is empty
        if (unitList.isEmpty()) {
            printCentered("No units have been currently allocated", FontColors.ANSI_RESET);
        } else {
            // print table of units
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

    public void displayUnitSelection(List<TeachingUnit> unitList, List<String> unitNames, boolean showUnitGrades) {
        // print options for user to select a unit from a given list
        // used in both view and update modes
        printStarBorder();
        // if only viewing unit data
        if (showUnitGrades) {
            printCentered("View grades", FontColors.ANSI_RESET);
            printCentered("Select unit to view grades", FontColors.ANSI_RESET);
        } else {
            // if in the update mode
            printCentered("Update grades", FontColors.ANSI_RESET);
            printCentered("Select unit to add/update grades", FontColors.ANSI_RESET);
        }
        if (unitList.isEmpty()) {
            // if unit list is empty for the user
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
        // print the list of enrollments for a selected unit
        printStarBorder();
        printCentered("Grade Report", FontColors.ANSI_RESET);
        printOnlyBorder();
        // show selected unit
        System.out.printf(MARGIN_LEFT+"* %-30s%"+(WIDTH-30-4)+"s *\n",
                "", "UNIT ID: "+unitId, "UNIT NAME: "+unitName);
        printStarBorder();
        if (unitList.isEmpty()) {
            printOnlyBorder();
            printCentered("No unit enrollments found", FontColors.ANSI_RESET);
        } else {
            // print table of enrollments
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
        printCentered("Press Enter to return to previous screen: ", FontColors.ANSI_RESET);
    }

    public void displayStudentMarks(Person student, List<EnrolledUnit> enrolledUnits,
                                    List<Unit> units, double avg, double gpa) {
        // prints marks list for a given student
        String name = student.getFirstName() + " " +student.getLastName();

        printStarBorder();
        printCentered("Grade Report", FontColors.ANSI_RESET);
        printOnlyBorder();
        // print student details
        System.out.printf(MARGIN_LEFT+"* %-30s%"+(WIDTH-30-4)+"s *\n",
                "", "STUDENT ID: "+student.getId(), "NAME: "+name);
        printStarBorder();
        // print enrollment data table
        System.out.printf(MARGIN_LEFT+"* %-10s%-25s%-15s%-10s%"+(WIDTH-60-4)+"s *\n",
                "", "UNIT ID", "UNIT NAME", "SEMESTER ID", "MARK", "GRADE");
        printDashBorder();
        for (EnrolledUnit unit: enrolledUnits) {
            Unit selectedUnit = units.stream()
                    .filter(unit1 -> unit1.getUnitId().equals(unit.getUnitId())).findFirst().get();
            System.out.printf(MARGIN_LEFT+"* %-10s%-25s%-15s%-10s%"+(WIDTH-60-4)+"s *\n",
                    "", unit.getUnitId(), selectedUnit.getUnitName(),
                    unit.getSemesterId(), unit.getMark(), unit.getGrade());
        }
        printOnlyBorder();
        printCentered("Average Mark for Student: "+String.format("%.2f", avg), FontColors.ANSI_RESET);
        printCentered("Student GPA: "+String.format("%.2f", gpa), FontColors.ANSI_RESET);
        printDashBorder();
    }

    public void displayStudentMenu(int currentIndex, int studentCount) {
        // print menu to select previous next student in the list
        printOnlyBorder();
        String prev = "[ - Previous Student";
        String next = "] - Next Student";
        List<String> options = new ArrayList<>();
        // if first student, display only next option
        if (currentIndex==0 && studentCount>1) {
            options.add(next);
        } else if (currentIndex==studentCount-1) {
            // if last student, display only previous option
            options.add(prev);
        } else {
            // show both options
            options.add(prev);
            options.add(next);
        }
        printCustomOptions(options.toArray(new String[0]));
        printOnlyBorder();
    }

    public void displayStudentMarkUpdateView(Person student, String unitId, String unitName, EnrolledUnit unit) {
        // print the view for updating student marks
        printStarBorder();
        printCentered("Update Marks for: "+unitId+" - "+unitName, FontColors.ANSI_RESET);
        printOnlyBorder();

        if (student!=null) {
            String name = student.getFirstName() + " " +student.getLastName();

            // show student details
            System.out.printf(MARGIN_LEFT+"* %-30s%"+(WIDTH-30-4)+"s *\n",
                    "", "STUDENT ID: "+student.getId(), "NAME: "+name);
            printStarBorder();
            // show current marks
            displayOneUnitData(unit);

        } else {
            printCentered("Student details not found!", FontColors.ANSI_RESET);
        }
    }

    public void displayUpdatedUnit(EnrolledUnit unit) {
        // print the unit after updating new marks data
        printDashBorder();
        printCentered("New marks saved.", FontColors.ANSI_RESET);
        printOnlyBorder();
        // show updated marks
        displayOneUnitData(unit);
    }

    private void displayOneUnitData(EnrolledUnit unit) {
        // print details of a given unit
        System.out.printf(MARGIN_LEFT+"* %-20s%-20s%-15s%"+(WIDTH-55-4)+"s *\n",
                "", "COURSE ID", "SEMESTER ID", "MARK", "GRADE");
        printDashBorder();
        System.out.printf(MARGIN_LEFT+"* %-20s%-20s%-15s%"+(WIDTH-55-4)+"s *\n",
                "", unit.getCourseId(), unit.getSemesterId(), unit.getMark(), unit.getGrade());
        printOnlyBorder();
    }
}
