// *****************************************************************
//   LecturerHomeView.java
//
//   This file contains the view for Lecturer Home screen.
// *****************************************************************


package view;

public class LecturerHomeView extends View {

    private static final String[] OPTIONS = {"View My Units", "Add Student Grades",
            "View Student Grades", "Update Student Grades"};

    public void displayView() {
        printOptions(OPTIONS);
    }

    public void promptUserChoice() {
        printUserPrompt(OPTIONS);
    }
}
