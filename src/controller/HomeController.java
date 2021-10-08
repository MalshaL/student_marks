// *****************************************************************
//   LoginController.java
//
//   This file contains the controller functions for Login screen.
// *****************************************************************


package controller;


import model.*;
import view.*;


public class HomeController implements Controller {

    private HomeView homeView;
    private User.UserLevel userLevel;

    public HomeController() {
        this.homeView = new HomeView();
    }

    public ResponseObject handle() {
        return null;
    }

    public void handle(User loggedInUser) {
        this.setUserLevel(loggedInUser.getUserLevel());
        homeView.showHomeView(loggedInUser.getUserLevel());
    }

    public User.UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(User.UserLevel userLevel) {
        this.userLevel = userLevel;
    }
}
