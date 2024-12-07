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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class QuestionController implements Initializable {

    // Needs the initalize method

    @FXML
    private VBox answerBox; // VBox from the FXML with fx:id="answerBox"

    @FXML
    private Text promptText;
    @FXML
    private Label scoreLabel;
    @FXML
    private Button skipButton;

    private SystemFACADE sf = SystemFACADE.getInstance();
    private int currentScore = 0;
    private Button selectedButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAnswerButtons();
        setPrompt();
    }

    public void handleCheckButton() {
        System.out.println("Check button clicked");
        if (sf.getCurrentQuestion().getAnswer().equals(selectedButton.getText())) {
            selectedButton.getStyleClass().add("highlight-correct");
            currentScore++;
            updateScore();
            skipButton.setText("Continue");
            skipButton.getStyleClass().add("highlight");
        } else {
            selectedButton.getStyleClass().add("highlight-incorrect");
        }
    }

    public void handleSkipButton() {
        System.out.println("Skip button clicked");
        // if (sf.getCurrentQuestion().getAnswer().equals(selectedButton.getText())) {
        sf.startNextQuestion();
        setAnswerButtons();
        setPrompt();
        // }

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
        Button answerButton = new Button(currentQuestion.getAnswer());
        answerButton.getStyleClass().add("question-answer-button");
        questionButtons.add(answerButton);
        // Add buttons for wrong answers
        if (currentQuestion.getWrongAnswers() != null) {
            for (String wrongAnswer : currentQuestion.getWrongAnswers()) {
                Button wrongAnswerButton = new Button(wrongAnswer);
                wrongAnswerButton.getStyleClass().add("question-answer-button");
                questionButtons.add(wrongAnswerButton);
            }
        }
        questionButtons.forEach(button -> button.setOnAction(event -> {
            questionButtons.forEach(b -> b.getStyleClass().remove("highlight"));
            if (button.getText().equals(sf.getCurrentQuestion().getAnswer())) {
                System.out.println("Correct!");
            }
            button.getStyleClass().add("highlight");
            this.selectedButton = button;
        }));
        Collections.shuffle(questionButtons);
        answerBox.getChildren().addAll(questionButtons);
    }

    public void updateScore() {
        int scoreValue = sf.getCurrentLesson().getUserScore();
        int maxScore = sf.getCurrentLesson().getMaxScore();

        // Clear the score Pane and add a new label with the formatted score
        scoreLabel.setText((scoreValue + 1) + " / " + maxScore);
        sf.getCurrentLesson().setUserScore(currentScore);
    }

    public void setPrompt() {
        Question currentQuestion = sf.getCurrentQuestion();
        if (currentQuestion == null)
            return;

        String prompt = currentQuestion.getPrompt();
        System.out.println("prompt: " + prompt + "\n");

        promptText.setText(prompt);
    }

}
