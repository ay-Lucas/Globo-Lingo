package com.model;
public interface Question {

    public boolean isCorrect();
    public void getUserInput(String input);
    public String getAnswer();
    public String getPrompt();
    public String[] getWrongAnswers();
}
