package com.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.Avatar;
import com.model.AvatarManager;
import com.model.User;
import com.model.UserList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UserController implements Initializable {
    @FXML
    private ComboBox<String> avatarComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button levelText;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;

    private AvatarManager avatarManager;
    private UserList userList;
    private User currentUser;
    private boolean editMode = false;
    private final String IMAGE_BASE_PATH = "./src/main/resources/images/";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarManager = new AvatarManager();
        userList = UserList.getInstance();

        setupImageView();
        populateAvatarComboBox();
        setupEventHandlers();
        initializeUserInterface();
    }

    private void setupImageView() {
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
    }

    private void populateAvatarComboBox() {
        for (Avatar avatar : avatarManager.getAllAvatars()) {
            avatarComboBox.getItems().add(avatar.getName());
        }
    }

    private void setupEventHandlers() {
        avatarComboBox.setOnAction(event -> updateAvatarImage());
        levelText.setOnAction(event -> upgradeLevel());
    }

    private void initializeUserInterface() {
        disableEditing();
        loadCurrentUser();
    }

    private void loadCurrentUser() {
        // Load a specific user from User.json - using "robbieWhite" as example
        currentUser = userList.getUser("robbieWhite");
        if (currentUser != null) {
            nameField.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            usernameField.setText(currentUser.getUsername());
            passwordField.setText(currentUser.getPassword());
            levelText.setText("Level: 9");

            Avatar userAvatar = currentUser.getAvatar();
            avatarComboBox.setValue(userAvatar.getName());
            updateAvatarImage();
        }
    }

    private void updateAvatarImage() {
        String selectedAvatarName = avatarComboBox.getValue();
        String imagePath = IMAGE_BASE_PATH +
                (avatarManager.getAvatar(selectedAvatarName).isDefault() ? "default2.0/" : "Loot_Crate2.0/") +
                selectedAvatarName + ".png";

        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);

            currentUser.setAvatar(avatarManager.getAvatar(selectedAvatarName));
            userList.saveUser(currentUser);
        }
    }

    @FXML
    private void handleEditButton() {
        editMode = true;
        enableEditing();
    }

    @FXML
    private void handleSaveButton() {
        if (editMode) {
            String[] nameParts = nameField.getText().split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";

            currentUser.updateDetails(
                    firstName,
                    lastName,
                    passwordField.getText(),
                    currentUser.getLevel(),
                    currentUser.getAvatar(),
                    currentUser.getCourseList());

            userList.saveUser(currentUser);
            editMode = false;
            disableEditing();
        }
    }

    private void upgradeLevel() {
        if (currentUser != null && currentUser.getLevel() < 10) {
            currentUser.setLevel(10);
            levelText.setText("Level: 10");
            levelText.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

            // Get the StackPane parent that contains the circles
            StackPane levelContainer = (StackPane) levelText.getParent();

            // Update outer circle (first circle)
            Circle outerCircle = (Circle) levelContainer.getChildren().get(0);
            outerCircle.setFill(Color.RED);
            outerCircle.setOpacity(0.29);

            // Update inner circle (second circle)
            Circle innerCircle = (Circle) levelContainer.getChildren().get(1);
            innerCircle.setFill(Color.RED);

            userList.saveUser(currentUser);
        }
    }

    private void enableEditing() {
        nameField.setEditable(true);
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        saveButton.setDisable(false);
    }

    private void disableEditing() {
        nameField.setEditable(false);
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        saveButton.setDisable(true);
    }

}
