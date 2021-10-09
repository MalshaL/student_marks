// *****************************************************************
//   LecturerController.java
//
//   This file contains the controller functions for user type 'Lecturer'.
// *****************************************************************


package controller;


import exception.InvalidInputException;
import model.*;
import view.*;


public class LecturerController implements Controller {

    private LecturerHomeView lecturerHomeView;

    public ResponseObject handle() {
        // show options for lecturer
        lecturerHomeView = new LecturerHomeView();
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
}
