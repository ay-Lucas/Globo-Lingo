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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import com.language.App;
import com.model.Language;
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

    @FXML
    private Pane progressCircleSection;
    private final static int MAX_LEVEL = 10;

    private SystemFACADE sf = SystemFACADE.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sf.initializeCourse();
        String userAvatarPath = sf.getCurrentUser().getAvatar().getPath();
        Image newImage = new Image(userAvatarPath);
        homeAvatarImage.setImage(newImage);
        lessonsCompletedLabel.setText(sf.getUserCourses().get(0).getCompletedLessons() + "/"
                + sf.getUserCourses().get(0).getLessons().size());
        setLessonButtons();
        setProgressCircle(sf.getCurrentUser().getLevel(), MAX_LEVEL); // Example: Level 2 of 10 (20%
        // progress)

    }

    public void setLessonButtons() {
        int completedLessons = sf.getUserCourses().get(0).getCompletedLessons();
        for (int i = 0; i < LESSON_NAMES.size(); i++) {
            Button button = new Button(i + 1 + ". " + LESSON_NAMES.get(i));
            if (i < completedLessons + 1) { // Add 1 to enable next lesson
                button.setId(String.valueOf(i));
                button.getStyleClass().add("lesson-button-enabled");
                button.setOnAction(event -> {
                    sf.setLesson(Integer.parseInt(button.getId()));
                    try {
                        App.setRoot("lesson");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            } else {
                button.getStyleClass().add("lesson-button-disabled");
            }
            lessonSectionButtonContainer.getChildren().add(button);
        }

    }

    private void setProgressCircle(int level, int maxLevel) {
        double progress = (double) level / maxLevel;

        Circle outerCircle = new Circle(100, 100, 90); // Adjust radii for spacing
        Color lightBlue = new Color(.443, .796, 1.0, 1.0);
        outerCircle.setFill(lightBlue);
        outerCircle.setStroke(Color.SLATEGREY);
        outerCircle.setStrokeWidth(25);

        Arc progressArc = new Arc(100, 100, 90, 90, 90, 0); // Start with no progress
        progressArc.setType(ArcType.OPEN);
        Color blue = new Color(.25, .584, .898, 1.0);
        progressArc.setStroke(blue);
        progressArc.setStrokeWidth(25);
        progressArc.setFill(Color.TRANSPARENT);
        progressArc.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

        double sweepAngle = -360 * progress;
        progressArc.setLength(sweepAngle);

        Label levelText = new Label("Level " + level);
        levelText.setLayoutX(68);
        levelText.setLayoutY(89);
        levelText.setFont(new Font(20));
        levelText.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        progressCircleSection.getChildren().clear();
        progressCircleSection.getChildren().addAll(outerCircle, progressArc, levelText);
    }

}
