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
import java.util.stream.Collectors;

public class LecturerHomeView extends View {

    private static final String[] OPTIONS = {"View My Units",
            "View Unit Grades", "View Student Grades", "Add/Update Student Grades"};

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

    public void promptUserChoice(String text) {
        printInputPrompt(text);
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

    public void displayUnitSelection(List<TeachingUnit> unitList, List<String> unitNames, boolean showUnitGrades) {
        printStarBorder();
        if (showUnitGrades) {
            printCentered("View grades", FontColors.ANSI_RESET);
            printCentered("Select unit to view grades", FontColors.ANSI_RESET);
        } else {
            printCentered("Update grades", FontColors.ANSI_RESET);
            printCentered("Select unit to add/update grades", FontColors.ANSI_RESET);
        }
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
        printCentered("Press Enter to return to previous screen: ", FontColors.ANSI_RESET);
    }

    public void displayStudentMarks(Person student, List<EnrolledUnit> enrolledUnits,
                                    List<Unit> units, double avg, double gpa) {
        String name = student.getFirstName() + " " +student.getLastName();

        printStarBorder();
        printCentered("Grade Report", FontColors.ANSI_RESET);
        printOnlyBorder();
        System.out.printf(MARGIN_LEFT+"* %-30s%"+(WIDTH-30-4)+"s *\n",
                "", "STUDENT ID: "+student.getId(), "NAME: "+name);
        printStarBorder();
        System.out.printf(MARGIN_LEFT+"* %-10s%-25s%-20s%-10s%"+(WIDTH-65-4)+"s *\n",
                "", "UNIT ID", "UNIT NAME", "SEMESTER ID", "MARK", "GRADE");
        printDashBorder();
        for (EnrolledUnit unit: enrolledUnits) {
            Unit selectedUnit = units.stream()
                    .filter(unit1 -> unit1.getUnitId().equals(unit.getUnitId())).findFirst().get();
            System.out.printf(MARGIN_LEFT+"* %-10s%-25s%-20s%-10s%"+(WIDTH-65-4)+"s *\n",
                    "", unit.getUnitId(), selectedUnit.getUnitName(),
                    unit.getSemesterId(), unit.getMark(), unit.getGrade());
        }
        printOnlyBorder();
        printCentered("Average Mark for Student: "+String.format("%.2f", avg), FontColors.ANSI_RESET);
        printCentered("Student GPA: "+String.format("%.2f", gpa), FontColors.ANSI_RESET);
        printDashBorder();
    }

    public void displayStudentMenu(int currentIndex, int studentCount) {
        printOnlyBorder();
        String prev = "[ - Previous Student";
        String next = "] - Next Student";
        List<String> options = new ArrayList<>();
        // if first student, display only next option
        if (currentIndex==0) {
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
}
