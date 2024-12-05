package com.controllers;

import java.io.IOException;

import com.language.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpController  {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Label invalidUsernameLabel;
    @FXML
    private Label invalidPasswordLabel;

    private void signUpPressed() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmField.getText();
        String phoneNumber = phoneNumberField.getText();
        if(!isPasswordValid(password))  {
            invalidPasswordLabel.setVisible(true);
        } else {
            if(App.getSystemFacade().createAccount(username, password, firstName, lastName))  {
                invalidPasswordLabel.setVisible(false); // For sanity sake
                invalidUsernameLabel.setVisible(false); // Ditto
                App.setRoot("login");
            } else {
                invalidUsernameLabel.setVisible(true);
            }
        }
    }

    private boolean isPasswordValid(String password)  {
        return password.length() >= 8 && password.length() <= 64;
    }

    private void existingAccountPressed() throws IOException {
        App.setRoot("login");
    }
}