// *****************************************************************
//   HomeController.java
//
//   This file contains the controller functions for Home screen.
// *****************************************************************


package controller;


import model.*;
import view.*;


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
        User.UserLevel userLevel = loggedInUser.getUserLevel();
        if (userLevel.equals(User.UserLevel.LECTURER)) {
            LecturerController lecturerController = new LecturerController(loggedInUser);
            lecturerController.handle();
        } else {
            System.out.println("none");
        }
        return null;
    }

}
