package com.controllers;

import java.io.IOException;

import javafx.fxml.FXML;

import com.language.App;
import com.model.SystemFACADE;

public class NavbarController {
    private SystemFACADE sf = SystemFACADE.getInstance();

    @FXML
    private void setHomeScene() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void setUserScene() throws IOException {
        App.setRoot("User");
    }

    @FXML
    private void setAvatarScene() throws IOException {
        App.setRoot("Avatar");
    }

    @FXML
    private void setLessonScene() throws IOException {
        App.setRoot("lesson");
    }

    @FXML
    private void logOut() throws IOException {
        sf.logout();
        App.setRoot("login");
    }

}
