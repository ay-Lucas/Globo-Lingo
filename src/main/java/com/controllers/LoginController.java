package com.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.language.App;

public class LoginController  {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    private void loginPressed() throws IOException  {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //TODO add login stuff using those two params, checking and then passing through facade.

        App.setRoot("home");
    }

    @FXML
    private void switchToSignUp() throws IOException  {
        App.setRoot("signUp");
    }

    @FXML
    private void playNarrator() throws IOException  {
        App.playNarrator();
    }
}