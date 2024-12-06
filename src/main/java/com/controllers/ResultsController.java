package com.controllers;

import com.language.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ResultsController  {

    @FXML private Pane topLeftPane;
    @FXML private Text topLeftPaneText;
    @FXML private Pane centerPane;
    @FXML private Text congratsOrFail;
    @FXML private Text passOrFail;
    @FXML private Text scoreOutOfMax;
    @FXML private Pane nextLessonPane;
    @FXML private Text nextLessonPaneText;
    @FXML private Button homeButton;
    @FXML private Button resultsButton;
    @FXML private Text lessonNameText;

    private boolean passed = App.getSystemFacade().getCurrentLesson().getPassed();
    private int score = App.getSystemFacade().getCurrentLesson().getUserScore();
    private int max = App.getSystemFacade().getCurrentLesson().getMaxScore();
    private String currentLessonName = App.getSystemFacade().getCurrentLesson().getName();

    public void Initialize()  {
        topLeftPaneText.setVisible(false);
        centerPane.setVisible(false);
        nextLessonPane.setVisible(false);
        lessonNameText.setText("Lesson: "+currentLessonName);
    }
    @FXML
    private void homeButtonPressed() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void resultsButtonPressed() throws IOException  {
        updateCenterPane();
        updateTopLeftPane();
        getNextLessonPaneText();
        topLeftPaneText.setVisible(true);
        centerPane.setVisible(true);
    }
    @FXML
    private void updateCenterPane() throws IOException  {
        if(passed)  {
            congratsOrFail.setText("Congratulations");
            passOrFail.setText("You Passed!");
            scoreOutOfMax.setText("You got "+score+" out of "+max+" correct.");
            nextLessonPane.setVisible(true);
        } else {
            congratsOrFail.setText("Better luck next time");
            passOrFail.setText("You Failed!");
            scoreOutOfMax.setText("You got "+score+" out of "+max+" correct.");
        }
    }
    @FXML 
    private void updateTopLeftPane() throws IOException  {
        topLeftPaneText.setText("Score "+score+"/"+max);
    }
    @FXML
    private void getNextLessonPaneText() throws IOException  {
        nextLessonPaneText.setText("Next Lesson: "+App.getSystemFacade().getCurrentCourse().getNextLesson().getName());
    }
}