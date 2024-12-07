package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.language.App;
import com.model.Question;
import com.model.SystemFACADE;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LessonController implements Initializable {
    @FXML
    Text lessonContent;
    @FXML
    Label lessonLabel;
    @FXML
    Button questionButton;
    private SystemFACADE sf = SystemFACADE.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int lessonNum = sf.getCurrentLesson().getLessonNumber();
        lessonLabel.setText("Lesson " + lessonNum + ": " + sf.getCurrentLesson().getName());
        lessonContent.setText(sf.getCurrentLesson().getContent());
        questionButton.setOnAction(event -> {
            sf.startNextQuestion();
            try {
                App.setRoot("question");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}
