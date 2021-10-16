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

    public List<Unit> getUnitsOfLecturer() {
        return unitsOfLecturer;
    }

    public void setUnitsOfLecturer() {
        // get the list of teaching units assigned to the current user
        List<TeachingUnit> teachingUnits = this.getTeachingUnitsOfLecturer();
        // set empty list if there are no units
        if (teachingUnits.isEmpty()) {
            this.unitsOfLecturer = Collections.emptyList();
        } else {
            // get the list of unit Ids from the teaching unit list
            List<String> unitIdList = teachingUnits
                    .stream()
                    .map(TeachingUnit::getLecturerId)
                    .collect(Collectors.toList());
            // extract the unit data for each unit Id in the above list
            this.unitsOfLecturer = getUnitsInList(unitIdList);
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
        // get the teaching units list of lecturer
        List<TeachingUnit> teachingUnits = this.getTeachingUnitsOfLecturer();
        // combine unit lists
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
}
