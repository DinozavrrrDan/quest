package com.nedorezov.service.impl;

import com.nedorezov.model.Content;
import com.nedorezov.model.Root;
import com.nedorezov.service.ContentService;
import com.nedorezov.service.GameService;
import lombok.Getter;

import java.util.ArrayList;

import static com.nedorezov.consts.WebConsts.GAME_START;

@Getter
public class GameServiceImpl implements GameService {
    private final Root root;

    private Content nextQuestion;

    public GameServiceImpl(ContentService contentService) {
        this.root = contentService.initRoot();
        findNextQuestion(GAME_START);
    }

    public ArrayList<String> getCurrentNextContent() {
        return root.getContent().stream()
                .filter(el -> el.equals(nextQuestion))
                .findFirst()
                .get().nextValues;
    }

    public void processAnswer(String answer) {
        findNextQuestion(answer);
    }

    public void resetGame() {
        findNextQuestion(GAME_START);
    }

    private void findNextQuestion(String code) {
        root.getContent().stream()
                .filter(el -> el.getCode().equals(code))
                .findFirst()
                .ifPresent(el -> nextQuestion = el);
    }
}
