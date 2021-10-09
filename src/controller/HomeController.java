// *****************************************************************
//   HomeController.java
//
//   This file contains the controller functions for Home screen.
// *****************************************************************


package controller;


import model.*;
import view.*;


public class HomeController implements Controller {

    private User.UserLevel userLevel;

    public HomeController() {
    }

    public ResponseObject handle() {
        return null;
    }

    public void handle(User loggedInUser) {
        this.setUserLevel(loggedInUser.getUserLevel());
        if (userLevel.equals(User.UserLevel.LECTURER)) {
            LecturerController lecturerController = new LecturerController();
            lecturerController.handle();
        } else {
            System.out.println("none");
        }
    }

    public User.UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(User.UserLevel userLevel) {
        this.userLevel = userLevel;
    }
}
