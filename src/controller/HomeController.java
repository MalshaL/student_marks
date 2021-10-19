// *****************************************************************
//   HomeController.java
//
//   This file contains the controller functions for Home screen.
// *****************************************************************


package controller;


import model.*;


public class HomeController implements Controller {

    private User loggedInUser;

    public HomeController(User loggedInUser) {
        this.setLoggedInUser(loggedInUser);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public ResponseObject handle() {
        // handle logged in user according to user type
        User.UserLevel userLevel = loggedInUser.getUserLevel();
        if (userLevel.equals(User.UserLevel.LECTURER)) {
            LecturerController lecturerController = new LecturerController(loggedInUser);
            return lecturerController.handle();
        } else {
            ResponseObject response = new ResponseObject();
            response.setMessage(ResponseCode.USER_EXIT);
            return response;
        }
    }

}
