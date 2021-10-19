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

    public ResponseObject handle() {
        boolean systemExit = false;
        ResponseObject response = new ResponseObject();

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

    private ResponseObject handleUserChoice() throws InvalidInputException {
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

    private ResponseObject handleSystemExit() {
        ResponseObject response = new ResponseObject();
        response.setMessage(ResponseCode.USER_EXIT);
        lecturerHomeView.handleUserExit(response);
        return response;
    }

    private ResponseObject handleViewUnits() {
        ResponseObject response = new ResponseObject();
        // get the teaching units list of lecturer
        List<TeachingUnit> teachingUnits = this.getTeachingUnitsOfLecturer();
        // get the unit names of teaching units
        List<String> unitNameList = teachingUnits
                .stream()
                .map(unit -> getUnitName(unit.getUnitId()))
                .collect(Collectors.toList());
        // print unit list
        lecturerHomeView.displayUnitData(teachingUnits, unitNameList);
        // wait for user to press enter after viewing results
        UserInput.getScanner().nextLine();
        response.setObject(teachingUnits);
        response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
        return response;
    }

    private String getUnitName(String unitId) {
        for (Unit unit: DataController.getInstance().getUnitList()) {
            if (unit.getUnitId().equals(unitId)) {
                return unit.getUnitName();
            }
        }
        return null;
    }

    private ResponseObject handleViewGrades() {
        return showUnitOptions(true);
    }

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

    private ResponseObject handleUpdateGrades() {
        ResponseObject response = new ResponseObject();
        showUnitOptions(false);

        return null;
    }

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
                                // give option to add/update grades of students for the selected unit
                                // display enrolments one by one to update marks
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

    private void displayUnitGrades(String unitId, String unitName, List<EnrolledUnit> enrolledUnits) {
        // get average mark for the unit
        double avg = getAverageUnitMark(enrolledUnits);
        // display grades for selected unit
        lecturerHomeView.displayUnitGrades(enrolledUnits,
                unitName, unitId, avg);
        // wait for user to press enter after viewing results
        UserInput.getScanner().nextLine();
    }

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

    private double getAverageUnitMark(List<EnrolledUnit> units) {
        double total = units.stream().mapToDouble(EnrolledUnit::getMark).sum();
        return total/units.size();
    }

    private double calculateGPA(List<EnrolledUnit> units) {
        double creditTotal = 0;
        double pointTotal = 0;
        if(units.isEmpty()) {
            return 0;
        }
        for (EnrolledUnit unit: units) {
            int credits = getUnitCredits(unit.getUnitId());
            creditTotal += credits;
            pointTotal += unit.getGrade().getGradeValue()*credits;
        }
        return pointTotal/creditTotal;
    }

    private int getUnitCredits(String unitId) {
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
