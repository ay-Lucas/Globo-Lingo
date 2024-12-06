package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.language.App;
import com.model.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class QuestionController {

    @FXML
    public static void handleCheckButton() {
    }

    @FXML
    public void setAnwserButtons() {
        Question currentQuestion = App.getSystemFacade().getCurrentQuestion();
        Button answerButton = new Button(currentQuestion.getAnswer());
        // placed in the vbox with the fxid "answerBox"
        
        if (currentQuestion.getWrongAnswers() != null) { // Check if the array exists
            for (int i = 0; i < currentQuestion.getWrongAnswers().length; i++) {
                Button wrongAnswerButton = new Button(currentQuestion.getWrongAnswers()[i]);
                // placed in the vbox  with the fxid "answerBox"
            }
        }
    }

    @FXML
    public void updateScore() {
        int score = App.getSystemFacade().getCurrentLesson().getUserScore();
        int maxScore = App.getSystemFacade().getCurrentLesson().getMaxScore();
        // Needs a label to insert in the pane with the fxid "score" that will have the format score / max score
    }

    @FXML void setPrompt() {
        Question currentQuestion = App.getSystemFacade().getCurrentQuestion();
        String prompt = currentQuestion.getPrompt();
        // needs a label to insert in the pane with the fxid "promptBox"
    }
}
