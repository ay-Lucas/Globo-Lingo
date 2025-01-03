package com.controllers;

import java.io.IOException;

import com.language.App;
import com.model.SystemFACADE;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label incorrectPassLabel;
    private SystemFACADE sf = SystemFACADE.getInstance();

    @FXML
    private void loginPressed() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (sf.login(username, password)) {
            incorrectPassLabel.setVisible(false); // Just for sanity sake.
            App.setRoot("home");
        } else {
            incorrectPassLabel.setVisible(true);
        }

    }

    @FXML
    private void switchToSignUp() throws IOException {
        App.setRoot("signup");
    }

    @FXML
    private void playNarrator() throws IOException {
        App.playNarrator();
    }
}
