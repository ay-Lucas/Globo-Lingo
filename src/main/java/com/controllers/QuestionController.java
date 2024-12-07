package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.language.App;
import com.model.NarratedQ;
import com.model.Question;
import com.model.SystemFACADE;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class QuestionController implements Initializable {

    @FXML
    private VBox answerBox;

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
        // Initialize with the first question
        handleSkipButton();
    }

    public void handleCheckButton() {
        System.out.println("Check button clicked");

        Question currentQuestion = sf.getCurrentQuestion();
        if (currentQuestion == null) {
            System.err.println("No current question to check.");
            return;
        }

        if (currentQuestion instanceof NarratedQ) {
            // Handle narrated question input
            if (!answerBox.getChildren().isEmpty() && answerBox.getChildren().get(1) instanceof TextField) {
                TextField textField = (TextField) answerBox.getChildren().get(1); // Second child is the TextField
                String userInput = textField.getText();

                if (((NarratedQ) currentQuestion).getAnswer().equalsIgnoreCase(userInput)) {
                    System.out.println("Correct!");
                    currentScore++;
                    updateScore();
                } else {
                    System.out.println("Incorrect!");
                }
            } else {
                System.err.println("Error: Expected a TextField in the answerBox.");
            }
        } else {
            // Handle multiple-choice question
            if (selectedButton != null && currentQuestion.getAnswer().equals(selectedButton.getText())) {
                selectedButton.getStyleClass().add("highlight-correct");
                currentScore++;
                updateScore();
                skipButton.setText("Continue");
                skipButton.getStyleClass().add("highlight");
            } else if (selectedButton != null) {
                selectedButton.getStyleClass().add("highlight-incorrect");
            } else {
                System.err.println("Error: No button selected.");
            }
        }
    }

    public void handleSkipButton() {
        System.out.println("Skip button clicked");

        Question nextQuestion = sf.startNextQuestion();
        if (nextQuestion == null) {
            System.out.println("No more questions.");
            return;
        }

        if (nextQuestion instanceof NarratedQ) {
            setNarratedQuestion();
        } else {
            setAnswerButtons();
            setPrompt();
        }
    }

    public void setNarratedQuestion() {
        // Clear previous answerBox content
        answerBox.getChildren().clear();

        // Create and add a button to play the narrated sound
        Button playSoundButton = new Button("Play Sound");
        playSoundButton.getStyleClass().add("question-answer-button");
        playSoundButton.setStyle("-fx-text-fill: white;");
        playSoundButton.setOnAction(event -> {
            NarratedQ narratedQuestion = (NarratedQ) sf.getCurrentQuestion();
            if (narratedQuestion != null) {
                narratedQuestion.playSound();
            }
        });

        // Create and add a TextField for user input
        TextField textField = new TextField();
        textField.setPromptText("Enter what you hear here");

        // Add components to the answerBox
        answerBox.getChildren().addAll(playSoundButton, textField);
    }

    public void setAnswerButtons() {
        Question currentQuestion = sf.getCurrentQuestion();
        if (currentQuestion == null)
            return;

        // Initialize the list of all answers
        ArrayList<String> answers = new ArrayList<>();
        answers.add(currentQuestion.getAnswer());

        // Add wrong answers if available
        if (currentQuestion.getWrongAnswers() != null) {
            answers.addAll(currentQuestion.getWrongAnswers());
        }

        // Clear the answerBox and create buttons
        answerBox.getChildren().clear();
        ArrayList<Button> answerButtons = new ArrayList<>();

        for (String answer : answers) {
            Button button = new Button(answer);
            button.getStyleClass().add("question-answer-button");
            button.setOnAction(event -> {
                answerButtons.forEach(b -> b.getStyleClass().remove("highlight"));
                button.getStyleClass().add("highlight");
                selectedButton = button;
            });
            answerButtons.add(button);
        }

        // Shuffle buttons and add to the answerBox
        Collections.shuffle(answerButtons);
        answerBox.getChildren().addAll(answerButtons);
    }

    public void updateScore() {
        int maxScore = sf.getCurrentLesson().getMaxScore();
        scoreLabel.setText(currentScore + " / " + maxScore);
        sf.getCurrentLesson().setUserScore(currentScore);
        if (this.currentScore > 2)
            sf.incrementUserLevel();
    }

    public void setPrompt() {
        Question currentQuestion = sf.getCurrentQuestion();
        if (currentQuestion == null)
            return;

        promptText.setText(currentQuestion.getPrompt());
    }
}
