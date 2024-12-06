package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.language.App;
import com.model.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class QuestionController {

    @FXML
    private VBox answerBox; // VBox from the FXML with fx:id="answerBox"

    @FXML
    private Pane promptBox; // Pane from the FXML with fx:id="promptBox"

    @FXML
    private Pane score; // Pane from the FXML with fx:id="score"

    @FXML
    public static void handleCheckButton() {
        // Handle the action of the "Check" button (Placeholder for now)
        System.out.println("Check button clicked");
    }

    @FXML
    public void setAnswerButtons() {
        Question currentQuestion = App.getSystemFacade().getCurrentQuestion();
        if (currentQuestion == null) return;

        // Clear the answerBox in case it already has buttons
        answerBox.getChildren().clear();

        // Add the correct answer button
        Button answerButton = new Button(currentQuestion.getAnswer());
        answerBox.getChildren().add(answerButton);

        // Add buttons for wrong answers
        if (currentQuestion.getWrongAnswers() != null) {
            for (String wrongAnswer : currentQuestion.getWrongAnswers()) {
                Button wrongAnswerButton = new Button(wrongAnswer);
                answerBox.getChildren().add(wrongAnswerButton);
            }
        }
    }

    @FXML
    public void updateScore() {
        int scoreValue = App.getSystemFacade().getCurrentLesson().getUserScore();
        int maxScore = App.getSystemFacade().getCurrentLesson().getMaxScore();

        // Clear the score Pane and add a new label with the formatted score
        score.getChildren().clear();
        Label scoreLabel = new Label(scoreValue + " / " + maxScore);
        score.getChildren().add(scoreLabel);
    }

    @FXML
    public void setPrompt() {
        Question currentQuestion = App.getSystemFacade().getCurrentQuestion();
        if (currentQuestion == null) return;

        String prompt = currentQuestion.getPrompt();

        // Clear the promptBox and add a new label with the prompt
        promptBox.getChildren().clear();
        Label promptLabel = new Label(prompt);
        promptBox.getChildren().add(promptLabel);
    }
}
