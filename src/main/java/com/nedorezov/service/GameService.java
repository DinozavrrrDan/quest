package com.nedorezov.service;

import com.nedorezov.model.Content;

import java.util.ArrayList;

public interface GameService {

    public ArrayList<String> getCurrentNextContent();
    public void processAnswer(String answer);
    public void resetGame();
    public Content getNextQuestion();
}
