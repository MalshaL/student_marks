// *****************************************************************
//   LecturerHomeView.java
//
//   This file contains the view for Lecturer Home screen.
// *****************************************************************


package view;

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
        printOptions(OPTIONS);
    }

    public void promptUserChoice() {
        printUserPrompt(OPTIONS);
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
}
