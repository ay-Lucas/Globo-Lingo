package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.language.App;
import com.model.Question;
import com.model.SystemFACADE;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class QuestionController implements Initializable {

    // Needs the initalize method

    @FXML
    private VBox answerBox; // VBox from the FXML with fx:id="answerBox"

    @FXML
    private Pane promptBox; // Pane from the FXML with fx:id="promptBox"

    @FXML
    private Pane score; // Pane from the FXML with fx:id="score"

    @FXML
    public void handleCheckButton() {
        // Handle the action of the "Check" button (Placeholder for now)
        System.out.println("Check button clicked");
    }

    @FXML
    static void handleSkipButton() {
        // Handle the action of the "Skip" button (Placeholder for now)
        System.out.println("Skip button clicked");
    }

    private SystemFACADE sf = SystemFACADE.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAnswerButtons();
        System.out.println(sf.getCurrentQuestion().getAnswer());
    }

    public void setAnswerButtons() {
        Question currentQuestion = sf.getCurrentQuestion();
        if (currentQuestion == null)
            return;

        ArrayList<String> questions = new ArrayList<>();
        questions.add(sf.getCurrentQuestion().getAnswer());
        for (String wrongAnswer : currentQuestion.getWrongAnswers()) {
            questions.add(wrongAnswer);
        }
        ArrayList<Button> questionButtons = new ArrayList<>();

        // Clear the answerBox in case it already has buttons
        answerBox.getChildren().clear();

        // Add the correct answer button
        // Button answerButton = new Button(currentQuestion.getAnswer());
        // answerButton.getStyleClass().add("question-answer-button");
        // questionButtons.add(answerButton);
        // Add buttons for wrong answers
        if (currentQuestion.getWrongAnswers() != null) {
            for (String wrongAnswer : currentQuestion.getWrongAnswers()) {
                Button wrongAnswerButton = new Button(wrongAnswer);
                wrongAnswerButton.getStyleClass().add("question-answer-button");
                questionButtons.add(wrongAnswerButton);
            }
        }
        questionButtons.forEach(e -> e.setOnAction(event -> {
            if (e.getText().equals(sf.getCurrentQuestion().getAnswer())) {
                System.out.println("Correct!");
            }
        }));
        Collections.shuffle(questionButtons);
        answerBox.getChildren().addAll(questionButtons);
    }

    public void updateScore() {
        int scoreValue = sf.getCurrentLesson().getUserScore();
        int maxScore = sf.getCurrentLesson().getMaxScore();

        // Clear the score Pane and add a new label with the formatted score
        score.getChildren().clear();
        Label scoreLabel = new Label(scoreValue + " / " + maxScore);
        score.getChildren().add(scoreLabel);
    }

    // public void setPrompt() {
    // Question currentQuestion = sf.getCurrentQuestion();
    // if (currentQuestion == null)
    // return;
    //
    // String prompt = currentQuestion.getPrompt();
    //
    // // Clear the promptBox and add a new label with the prompt
    // promptBox.getChildren().clear();
    // Label promptLabel = new Label(prompt);
    // promptBox.getChildren().add(promptLabel);
    // }

}
