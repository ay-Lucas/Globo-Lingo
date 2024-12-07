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
        setAnswerButtons();
        setPrompt();
    }

    public void handleCheckButton() {
        System.out.println("Check button clicked");

        if (sf.getCurrentQuestion() instanceof NarratedQ) {
            // Handle narrated question input
            TextField textField = (TextField) answerBox.getChildren().get(0);
            String userInput = textField.getText();

            if (((NarratedQ) sf.getCurrentQuestion()).getAnswer().equalsIgnoreCase(userInput)) {
                System.out.println("Correct!");
                currentScore++;
                updateScore();
            } else {
                System.out.println("Incorrect!");
            }
        } else {
            // Handle multiple-choice question
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
    }

    public void handleSkipButton() {
        System.out.println("Skip button clicked");
        Question question = sf.startNextQuestion();

        if (question == null) {
            System.out.println("No more questions.");
            return;
        }

        if (question instanceof NarratedQ) {
            setNarratedQuestion();

        } else {
            setAnswerButtons();
            setPrompt();
        }

    }

    public void setNarratedQuestion() {
        // Clear previous answerBox content
        answerBox.getChildren().clear();

        // Create and add a TextField for user input
        TextField textField = new TextField();
        textField.setPromptText("Enter what you hear here");

        // Create and add a button to play the narrated sound
        Button playSoundButton = new Button("Play Sound");
        playSoundButton.setOnAction(event -> {
            NarratedQ narratedQuestion = (NarratedQ) sf.getCurrentQuestion();
            if (narratedQuestion != null) {
                narratedQuestion.playSound();
            }
        });

        // Add TextField and button to the VBox
        answerBox.getChildren().addAll(playSoundButton, textField);
    }

    public void setAnswerButtons() {
        Question currentQuestion = sf.getCurrentQuestion();
        if (currentQuestion == null)
            return;

        // Initialize the list of all answers
        ArrayList<String> questions = new ArrayList<>();
        questions.add(currentQuestion.getAnswer());

        // Safely handle the case where getWrongAnswers() is null
        if (currentQuestion.getWrongAnswers() != null) {
            for (String wrongAnswer : currentQuestion.getWrongAnswers()) {
                questions.add(wrongAnswer);
            }
        }

        // Create buttons
        ArrayList<Button> questionButtons = new ArrayList<>();

        // Clear the answerBox in case it already has buttons
        answerBox.getChildren().clear();

        for (String answer : questions) {
            Button button = new Button(answer);
            button.getStyleClass().add("question-answer-button");
            questionButtons.add(button);

            // Set the button action
            button.setOnAction(event -> {
                questionButtons.forEach(b -> b.getStyleClass().remove("highlight"));
                button.getStyleClass().add("highlight");
                this.selectedButton = button;
            });
        }

        // Shuffle the buttons and add them to the answerBox
        Collections.shuffle(questionButtons);
        answerBox.getChildren().addAll(questionButtons);
    }

    public void updateScore() {
        int scoreValue = sf.getCurrentLesson().getUserScore();
        int maxScore = sf.getCurrentLesson().getMaxScore();

        scoreLabel.setText(currentScore + " / " + maxScore);
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
