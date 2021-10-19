// *****************************************************************
//   LecturerController.java
//
//   This file contains the controller functions for user type 'Lecturer'.
// *****************************************************************


package controller;

import java.util.*;
import java.util.stream.Collectors;

import exception.InvalidInputException;
import model.*;
import view.*;


public class LecturerController implements Controller {

    private final LecturerHomeView lecturerHomeView;
    // currently logged in user
    private User loggedInUser;
    // units taught by the logged in lecturer
    private List<TeachingUnit> teachingUnitsOfLecturer;

    public LecturerController(User loggedInUser) {
        this.setLoggedInUser(loggedInUser);
        lecturerHomeView = new LecturerHomeView();
        // get the units taught by lecturer when user logs in
        this.setTeachingUnitsOfLecturer();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<TeachingUnit> getTeachingUnitsOfLecturer() {
        return teachingUnitsOfLecturer;
    }

    public void setTeachingUnitsOfLecturer() {
        // get all units being taught at the university
        List<TeachingUnit> teachingUnits = DataController.getInstance().getTeachingUnits();
        // set empty list if there are no units
        if (teachingUnits.isEmpty()) {
            this.teachingUnitsOfLecturer = Collections.emptyList();
        } else {
            // get the current user's user id
            String lecturerId = this.getLoggedInUser().getUserId();
            // get the list of units taught by the current user
            this.teachingUnitsOfLecturer = teachingUnits
                    .stream()
                    // filter to get the units with the same user id
                    .filter(unit -> unit.getLecturerId().equals(lecturerId))
                    .collect(Collectors.toList());
        }
    }

    /**
     * This method is directly callable form outside.
     * Handles main functionalities in the Lecturer View.
     * @return Response object containing a response code
     */
    public ResponseObject handle() {
        boolean systemExit = false;
        ResponseObject response = new ResponseObject();
        // loops until user enters exit option
        while (!systemExit) {
            // show options for lecturer
            lecturerHomeView.displayView();
            boolean invalidInput = true;
            while (invalidInput) {
                try {
                    // obtain user choice input
                    lecturerHomeView.promptUserChoice();
                    response = handleUserChoice();
                    invalidInput = false;
                    // if user chooses to exit
                    if(response.getMessage().equals(ResponseCode.USER_EXIT)) {
                        systemExit = true;
                    }
                } catch (InvalidInputException e) {
                    // handle invalid input
                    response.setMessage(ResponseCode.INVALID_INPUT);
                    lecturerHomeView.printUserChoiceError(response);
                }
            }
        }
        return response;
    }

    /**
     * Presents main menu for Lecturer and handles user inputs.
     * @return Response object
     * @throws InvalidInputException when user input is invalid
     */
    private ResponseObject handleUserChoice() throws InvalidInputException {
        // get user input
        String input = UserInput.getScanner().nextLine();
        // remove whitespace in input
        input = input.replaceAll("\\s","");
        ResponseObject response;
        try {
            int intValue = Integer.parseInt(input);
            switch(intValue){
                case 0:
                    // exit the System
                    response = handleSystemExit();
                    break;
                case 1:
                    // view units assigned to the lecturer
                    response = handleViewUnits();
                    break;
                case 2:
                    // view grades for a unit assigned to the lecturer
                    response = handleViewGrades();
                    break;
                case 3:
                    // view all grades of a student
                    response = handleStudentGrades();
                    break;
                case 4:
                    // add or update student grades for a unit
                    response = handleUpdateGrades();
                    break;
                default:
                    // throw exception for invalid input
                    throw new InvalidInputException("Invalid input", null);
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid input", e);
        }
        return response;
    }

    /**
     * Handles system exit when user enters exit option
     * @return Response object
     */
    private ResponseObject handleSystemExit() {
        ResponseObject response = new ResponseObject();
        response.setMessage(ResponseCode.USER_EXIT);
        lecturerHomeView.handleUserExit(response);
        return response;
    }

    /**
     * Handles displaying the list of units taught by the lecturer
     * @return Response Object
     */
    private ResponseObject handleViewUnits() {
        ResponseObject response = new ResponseObject();
        // get the teaching units list of lecturer
        List<TeachingUnit> teachingUnits = this.getTeachingUnitsOfLecturer();
        // get the unit names of teaching units
        List<String> unitNameList = teachingUnits
                .stream()
                .map(unit -> getUnitName(unit.getUnitId()))
                .collect(Collectors.toList());
        // show unit list
        lecturerHomeView.displayUnitData(teachingUnits, unitNameList);
        // wait for user to press enter after viewing results
        UserInput.getScanner().nextLine();
        // set response
        response.setObject(teachingUnits);
        response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
        return response;
    }

    /**
     * Utility method to get Unit name given a unit ID
     * @param unitId ID of the unit to search
     * @return unit name
     */
    private String getUnitName(String unitId) {
        for (Unit unit: DataController.getInstance().getUnitList()) {
            if (unit.getUnitId().equals(unitId)) {
                return unit.getUnitName();
            }
        }
        return null;
    }

    /**
     * Handles displaying grades for each unit taught by the lecturer
     * @return Response Object
     */
    private ResponseObject handleViewGrades() {
        return showUnitOptions( true);
    }

    /**
     * Handles displaying grades of a single student
     * @return Response Object
     */
    private ResponseObject handleStudentGrades() {
        ResponseObject response = new ResponseObject();
        // get the student list
        List<Person> allStudents = DataController.getInstance().getStudentList();
        // get the enrolled unit list
        List<EnrolledUnit> enrolledUnits = DataController.getInstance().getEnrolledUnits();
        // user selecting to cancel and go to previous screen
        boolean goBack = false;
        // index of the student being displayed
        int currentIndex = 0;
        while (!goBack) {
            // student to display
            Person selectedStudent = allStudents.get(currentIndex);
            // get units for student
            List<EnrolledUnit> studentUnits = enrolledUnits.stream()
                    .filter(enrolledUnit -> enrolledUnit.getStudentId().equals(selectedStudent.getId()))
                    .collect(Collectors.toList());
            // get unit names list
            List<Unit> selectedUnits = getUnitsInList(studentUnits.stream()
                    .map(EnrolledUnit::getUnitId).collect(Collectors.toList()));
            // display marks
            lecturerHomeView.displayStudentMarks(selectedStudent,
                    studentUnits, selectedUnits, getAverageUnitMark(studentUnits), calculateGPA(studentUnits));
            // display menu to select
            lecturerHomeView.displayStudentMenu(currentIndex, allStudents.size());
            // loop till user enters valid input
            boolean invalidInput = true;
            // create list of valid inputs
            List<String> validInputs = getValidStudentMenuInputs(currentIndex, allStudents.size());
            while (invalidInput) {
                // print input prompt to user
                lecturerHomeView.promptUserChoice("Enter your choice : ");
                // get user input
                String input = UserInput.getScanner().nextLine();
                // remove whitespace in input
                input = input.replaceAll("\\s","");
                // if input is valid
                if(validInputs.contains(input)) {
                    invalidInput = false;
                    response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
                    switch (input) {
                        case "0":
                            // if user has selected to cancel and go back
                            goBack = true;
                            break;
                        case "[":
                            // prev student
                            currentIndex = currentIndex-1;
                            break;
                        case "]":
                            // next student
                            currentIndex += 1;
                            break;
                    }
                } else {
                    response.setMessage(ResponseCode.INVALID_INPUT);
                    lecturerHomeView.printUserChoiceError(response);
                }
            }
        }
        return response;
    }

    /**
     * Handles updating grades of students taught by the lecturer
     * @return Response Object
     */
    private ResponseObject handleUpdateGrades() {
        return showUnitOptions(false);
    }

    /**
     * Utility method used to display units for the user to select
     * and prompts to display grades or update grades
     * @param showUnitGrades if true, grades are displayed in read-only mode
     * @return Response Object
     */
    private ResponseObject showUnitOptions(boolean showUnitGrades) {
        ResponseObject response = new ResponseObject();
        // get the teaching units list of lecturer
        List<TeachingUnit> teachingUnits = this.getTeachingUnitsOfLecturer();
        // get the unit names of teaching units
        List<String> unitNameList = teachingUnits
                .stream()
                .map(unit -> getUnitName(unit.getUnitId()))
                .collect(Collectors.toList());

        // user has selected to go the previous screen
        boolean goBack = false;
        // until the back option is not selected
        while (!goBack) {
            // show unit options for lecturer
            lecturerHomeView.displayUnitSelection(teachingUnits, unitNameList, showUnitGrades);
            boolean invalidInput = true;
            while (invalidInput) {
                // obtain user choice input
                // print input prompt to user
                lecturerHomeView.promptUserChoice(unitNameList.size());
                // get user input
                String input = UserInput.getScanner().nextLine();
                // remove whitespace in input
                input = input.replaceAll("\\s","");
                try {
                    // attempt to convert input to int
                    int selectedUnit = Integer.parseInt(input);
                    // check if input is within range
                    if (0 <= selectedUnit && selectedUnit <= teachingUnits.size()) {
                        // set invalid flag to false to indicate user input is valid
                        invalidInput = false;
                        // if user has selected to cancel and go back
                        if (selectedUnit == 0) {
                            goBack = true;
                        } else {
                            // get unit Id for selected unit
                            String unitId = teachingUnits.get(selectedUnit - 1).getUnitId();
                            // get unit name
                            String unitName = unitNameList.get(selectedUnit - 1);
                            // get enrolled unit list for selected unit
                            List<EnrolledUnit> enrolledUnits = getEnrolledUnits(unitId);
                            if(showUnitGrades) {
                                // display grades for the selected unit
                                displayUnitGrades(unitId, unitName, enrolledUnits);
                            } else {
                                if (enrolledUnits.isEmpty()) {
                                    // no students enrolled in selected unit
                                    response.setMessage(ResponseCode.EMPTY_LIST);
                                    lecturerHomeView.printUserChoiceInfo(response);
                                    invalidInput = true;
                                } else {
                                    response = updateUnitGrades(enrolledUnits, unitId, unitName);
                                }
                            }
                        }
                        response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
                    } else {
                        response.setMessage(ResponseCode.INVALID_INPUT);
                        lecturerHomeView.printUserChoiceError(response);
                    }
                } catch (NumberFormatException e) {
                    // handle invalid input
                    response.setMessage(ResponseCode.INVALID_INPUT);
                    lecturerHomeView.printUserChoiceError(response);
                }
            }
        }
        return response;
    }

    private ResponseObject updateUnitGrades(List<EnrolledUnit> enrolledUnits, String unitId, String unitName) {
        ResponseObject response = new ResponseObject();
        // give option to add/update grades of students for the selected unit
        boolean goToPrev = false;
        // index of the enrollment being displayed
        int currentIndex = 0;
        while (!goToPrev) {
            // selected enrollment
            EnrolledUnit enrolledUnit = enrolledUnits.get(currentIndex);
            // get student in the selected unit
            Person student = getEnrolledStudent(enrolledUnit.getStudentId());
            // display enrolments one by one to update marks
            lecturerHomeView.displayStudentMarkUpdateView(student,
                    unitId, unitName, enrolledUnit);
            // loop till user enters valid input
            boolean invalidMark = true;
            // create list of valid inputs
            while (invalidMark) {
                try {
                    // prompt user to enter marks
                    lecturerHomeView.promptUserChoice("Enter mark : ");
                    // get user input and remove whitespace
                    String markString = UserInput.getScanner().nextLine()
                            .replaceAll("\\s", "");
                    double marksInput = Double.parseDouble(markString);
                    if (0 <= marksInput && marksInput <= 100) {
                        invalidMark = false;
                        // set marks in enrolled unit
                        enrolledUnit.setMark(marksInput);
                        // get unit with updated grade
                        EnrolledUnit enrolledUnitUpdated = saveNewMark(enrolledUnit);
                        // display updated unit
                        lecturerHomeView.displayUpdatedUnit(enrolledUnitUpdated);
                    } else {
                        // handle invalid input
                        response.setMessage(ResponseCode.INVALID_INPUT);
                        lecturerHomeView.printUserChoiceError(response);
                    }
                } catch (NumberFormatException e) {
                    // handle invalid input
                    response.setMessage(ResponseCode.INVALID_INPUT);
                    lecturerHomeView.printUserChoiceError(response);
                }
            }
            // display menu to select next enrollment to edit
            lecturerHomeView.displayStudentMenu(currentIndex, enrolledUnits.size());
            // loop till user enters valid input
            boolean invalidOption = true;
            // create list of valid inputs
            List<String> validInputs = getValidStudentMenuInputs(currentIndex, enrolledUnits.size());
            while (invalidOption) {
                // print input prompt to user
                lecturerHomeView.promptUserChoice("Enter your choice : ");
                // get user input
                String inputChoice = UserInput.getScanner().nextLine();
                // remove whitespace in input
                inputChoice = inputChoice.replaceAll("\\s", "");
                // if input is valid
                if (validInputs.contains(inputChoice)) {
                    invalidOption = false;
                    response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
                    switch (inputChoice) {
                        case "0":
                            // if user has selected to cancel and go back
                            goToPrev = true;
                            break;
                        case "[":
                            // prev student
                            currentIndex = currentIndex - 1;
                            break;
                        case "]":
                            // next student
                            currentIndex += 1;
                            break;
                    }
                } else {
                    response.setMessage(ResponseCode.INVALID_INPUT);
                    lecturerHomeView.printUserChoiceError(response);
                }
            }
        }
        return response;
    }

    /**
     * Utility method to show grades of a selected unit
     * @param unitId selected unit Id
     * @param unitName selected unit name
     * @param enrolledUnits list of enrollments for the unit
     */
    private void displayUnitGrades(String unitId, String unitName, List<EnrolledUnit> enrolledUnits) {
        // get average mark for the unit
        double avg = getAverageUnitMark(enrolledUnits);
        // display grades for selected unit
        lecturerHomeView.displayUnitGrades(enrolledUnits,
                unitName, unitId, avg);
        // wait for user to press enter after viewing results
        UserInput.getScanner().nextLine();
    }

    /**
     * Utility method to get valid set of inputs when looping through list
     * @param currentIndex current index of the list
     * @param studentCount total count of the list
     * @return valid list of inputs
     */
    private List<String> getValidStudentMenuInputs(int currentIndex, int studentCount) {
        List<String> validInputs = new ArrayList<>();
        validInputs.add("0");
        // if first student, only next option is valid
        if (currentIndex==0) {
            validInputs.add("]");
        } else if (currentIndex==studentCount-1) {
            // if last student, only previous option is valid
            validInputs.add("[");
        } else {
            // both options are valid
            validInputs.add("]");
            validInputs.add("[");
        }
        return validInputs;
    }

    /**
     * Utility method to get the list of units given a list of unitIds
     * @param unitIds list of unit Ids
     * @return list of units
     */
    private List<Unit> getUnitsInList(List<String> unitIds) {
        // get all units saved in the system
        List<Unit> allUnits = DataController.getInstance().getUnitList();
        // set empty list if there are no units
        if (allUnits.isEmpty()) {
            return Collections.emptyList();
        }
        // convert unit Id list to a set to reduce traverse time
        Set<String> ids = new HashSet<>(unitIds);
        // filter the unit list to get the units for the current user
        return allUnits
                .stream().filter(unit -> ids.contains(unit.getUnitId()))
                .collect(Collectors.toList());
    }

    /**
     * Get list of enrollments given a unit Id
     * @param unitId unit Id to search
     * @return List of enrollments
     */
    private List<EnrolledUnit> getEnrolledUnits(String unitId) {
        // get all enrolled units saved in the system
        List<EnrolledUnit> units = DataController.getInstance().getEnrolledUnits();
        // set empty list if there are no units
        if (units.isEmpty()) {
            return Collections.emptyList();
        }
        // get list of enrolled units with same unit id
        return units.stream().filter(unit -> unit.getUnitId().equals(unitId)).collect(Collectors.toList());
    }

    /**
     * Get details of student given studentId
     * @param studentId studentId
     * @return Student wrapped in a Person object
     */
    private Person getEnrolledStudent(String studentId) {
        Optional<Person> student = DataController.getInstance().getStudentList().stream()
                .filter(s->s.getId().equals(studentId))
                .findFirst();
        if (student.isPresent()) {
            return student.get();
        }
        return null;
    }

    /**
     * Updates the new mark input by the user
     * @param unit enrollment to update
     * @return Updated enrollment
     */
    private EnrolledUnit saveNewMark(EnrolledUnit unit) {
        // set grade for the marks
        unit.setGrade(getGradeForMark(unit.getMark()));
        // get the complete enrolled unit list
        List<EnrolledUnit> units = DataController.getInstance().getEnrolledUnits();
        // iterate to replace updated unit
        ListIterator<EnrolledUnit> iterator = units.listIterator();
        while (iterator.hasNext()) {
            EnrolledUnit enrolledUnit = iterator.next();
            // filter to find the unit
            if (enrolledUnit.getUnitId().equals(unit.getUnitId()) &&
                    enrolledUnit.getStudentId().equals(unit.getStudentId()) &&
                    enrolledUnit.getCourseId().equals(unit.getCourseId()) &&
                    enrolledUnit.getSemesterId().equals(unit.getSemesterId())) {
                // replace unit
                iterator.set(unit);
            }
        }
        return unit;
    }

    /**
     * get the Grade for the updated mark
     * @param mark mark input by user
     * @return new Grade
     */
    private Grade getGradeForMark(double mark) {
        // get the grade for the given mark
        Grade[] allGrades = Grade.values();
        for (Grade g: allGrades) {
            // check if the mark is within range
            if (mark >= g.getLowerBound() && mark <= g.getHigherBound()) {
                return g;
            }
        }
        return null;
    }

    /**
     * Get the average mark for a unit
     * @param units list of enrollments
     * @return average mark
     */
    private double getAverageUnitMark(List<EnrolledUnit> units) {
        double total = 0;
        int unitCount = 0;
        for (EnrolledUnit unit: units) {
            // if the grade is available for the unit
            if (!unit.getGrade().equals(Grade.NA)) {
                // get the marks for the unit
                total += unit.getMark();
                unitCount += 1;
            }
        }
        // prevent division by zero
        if (unitCount>0) {
            return total/unitCount;
        } else {
            return 0;
        }
    }

    /**
     * Calculates the GPA of a student
     * @param units enrollments of a student
     * @return gpa value
     */
    private double calculateGPA(List<EnrolledUnit> units) {
        double creditTotal = 0;
        double pointTotal = 0;
        if(units.isEmpty()) {
            return 0;
        }
        for (EnrolledUnit unit: units) {
            // if the grade is available for the unit
            if (!unit.getGrade().equals(Grade.NA)) {
                // get the number of credit points for the unit
                int credits = getUnitCredits(unit.getUnitId());
                creditTotal += credits;
                pointTotal += unit.getGrade().getGradeValue() * credits;
            }
        }
        return pointTotal/creditTotal;
    }

    /**
     * Get the number of credits for a unit to calculate GPA
     * @param unitId unit Id to get credits
     * @return credit points of the unit
     */
    private int getUnitCredits(String unitId) {
        // filter to find the unit and get the number of credits
        Optional<Integer> val = DataController.getInstance().getUnitList().stream()
                .filter(unit -> unit.getUnitId().equals(unitId))
                .map(Unit::getCredits)
                .findFirst();
        if (val.isPresent()) {
            return val.get();
        }
        return 0;
    }
}
