package com.nedorezov.service.impl;

import com.nedorezov.model.Content;
import com.nedorezov.model.Root;
import com.nedorezov.service.ContentService;
import com.nedorezov.service.GameService;
import lombok.Getter;

import java.util.ArrayList;


@Getter
public class GameServiceImpl implements GameService {
    private final Root root;

    private Content nextQuestion;

    public GameServiceImpl(ContentService contentService) {
        this.root = contentService.initRoot();
        findNextQuestion("gameStart");

    }

    public ArrayList<String> getCurrentNextContent() {
        return root.content.stream()
                .filter(el -> el.equals(nextQuestion))
                .findFirst()
                .get().nextValues;
    }

    public void processAnswer(String answer) {
        findNextQuestion(answer);
    }

    public void resetGame() {
        findNextQuestion("gameStart");
    }

    private void findNextQuestion(String code) {
        root.content.stream()
                .filter(el -> el.getCode().equals(code))
                .findFirst()
                .ifPresent(el -> nextQuestion = el);
    }
}
