// *****************************************************************
//   HomeView.java
//
//   This file contains the view for Home screen.
// *****************************************************************


package view;

import model.*;

public class HomeView extends View {

    public HomeView() {

    }

    public void showHomeView(User.UserLevel userLevel) {
        if (userLevel.equals(User.UserLevel.LECTURER)) {
            // show options for lecturer
            showLecturerHome();
        }
    }

    private void showLecturerHome() {

    }
}
