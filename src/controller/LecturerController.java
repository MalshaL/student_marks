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
    private User loggedInUser;
    private List<TeachingUnit> teachingUnitsOfLecturer;
    private List<Unit> unitsOfLecturer;

    public LecturerController(User loggedInUser) {
        this.setLoggedInUser(loggedInUser);
        lecturerHomeView = new LecturerHomeView();
        this.setTeachingUnitsOfLecturer();
        this.setUnitsOfLecturer();
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
        List<TeachingUnit> teachingUnits = DataController.getInstance().getTeachingUnits();
        if (teachingUnits.isEmpty()) {
            this.teachingUnitsOfLecturer = Collections.emptyList();
        } else {
            String lecturerId = this.getLoggedInUser().getUserId();
            this.teachingUnitsOfLecturer = teachingUnits
                    .stream()
                    .filter(unit -> unit.getLecturerId().equals(lecturerId))
                    .collect(Collectors.toList());
        }
    }

    public List<Unit> getUnitsOfLecturer() {
        return unitsOfLecturer;
    }

    public void setUnitsOfLecturer() {
        List<TeachingUnit> teachingUnits = this.getTeachingUnitsOfLecturer();
        if (teachingUnits.isEmpty()) {
            this.unitsOfLecturer = Collections.emptyList();
        } else {
            List<String> unitIdList = teachingUnits
                    .stream()
                    .map(TeachingUnit::getLecturerId)
                    .collect(Collectors.toList());
            this.unitsOfLecturer = getUnitsInList(unitIdList);
        }
    }

    public ResponseObject handle() {
        // show options for lecturer
        lecturerHomeView.displayView();
        ResponseObject response = new ResponseObject();

        boolean invalidInput = true;
        while (invalidInput) {
            try {
                // obtain user choice input
                lecturerHomeView.promptUserChoice();
                response = handleUserChoice();
                invalidInput = false;
            } catch (InvalidInputException e) {
                // handle invalid input
                response.setMessage(ResponseCode.INVALID_INPUT);
                lecturerHomeView.printUserChoiceError(response);
            }
        }
        return response;
    }

    private ResponseObject handleUserChoice() throws InvalidInputException {
        String input = UserInput.getScanner().nextLine();
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
                    // add student grades for a unit
                    response = handleAddGrades();
                    break;
                case 3:
                    // view student grades for a unit
                    response = handleViewGrades();
                    break;
                case 4:
                    // update student grades for a unit
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
        List<Unit> unitsOfLecturer = this.getUnitsOfLecturer();
        if (unitsOfLecturer.isEmpty()) {
            // inform that no units were found for user
        } else {
            // print unit list
        }
        // display back option to go to previous screen
        response.setObject(unitsOfLecturer);
        response.setMessage(ResponseCode.DATA_QUERY_SUCCESSFUL);
        return response;
    }

    private ResponseObject handleAddGrades() {
        return null;
    }

    private ResponseObject handleViewGrades() {
        return null;
    }

    private ResponseObject handleUpdateGrades() {
        return null;
    }

    private List<Unit> getUnitsInList(List<String> unitIds) {
        List<Unit> allUnits = DataController.getInstance().getUnitList();
        if (allUnits.isEmpty()) {
            return Collections.emptyList();
        }
        // convert list to a set to reduce traverse time
        Set<String> ids = new HashSet<>(unitIds);
        return allUnits
                .stream().filter(unit -> ids.contains(unit.getUnitId()))
                .collect(Collectors.toList());
    }
}
