package com.model;

import java.util.List;

public interface Question {

    public boolean isCorrect();

    public void getUserInput(String input);

    public String getAnswer();

    public String getPrompt();

    public List<String> getWrongAnswers();
}
