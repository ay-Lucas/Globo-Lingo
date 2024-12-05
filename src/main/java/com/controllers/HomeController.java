package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import com.language.App;
import com.model.SystemFACADE;

public class HomeController extends NavbarController implements Initializable {
    private final List<String> LESSON_NAMES = List.of(
            "Basics", "Greetings", "Travel", "Food", "Family & Relationships", "Household Items",
            "Occupations & Professions", "Education & School", "Shopping & Consumer Goods", "Sports & Hobbies");
    @FXML
    private ImageView homeAvatarImage;

    @FXML
    private VBox lessonSectionButtonContainer;

    @FXML
    private Label lessonsCompletedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userAvatarPath = App.getSystemFacade().getCurrentUser().getAvatar().getPath();
        Image newImage = new Image(userAvatarPath);
        homeAvatarImage.setImage(newImage);
        lessonsCompletedLabel.setText(App.getSystemFacade().getUserCourses().get(0).getCompletedLessons() + "/"
                + App.getSystemFacade().getUserCourses().get(0).getLessons().size());
        setLessonButtons();

    }

    public void setLessonButtons() {
        int completedLessons = App.getSystemFacade().getUserCourses().get(0).getCompletedLessons();
        for (int i = 0; i < LESSON_NAMES.size(); i++) {
            Button button = new Button(i + 1 + ". " + LESSON_NAMES.get(i));
            if (i < completedLessons) {
                button.getStyleClass().add("lesson-button-enabled");
                button.setOnAction(event -> {
                    try {
                        App.setRoot("lesson");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            } else
                button.getStyleClass().add("lesson-button-disabled");
            lessonSectionButtonContainer.getChildren().add(button);
        }

    }

}
