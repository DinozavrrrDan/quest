package com.nedorezov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedorezov.service.ContentService;
import com.nedorezov.service.GameService;
import com.nedorezov.service.JsonParseService;
import com.nedorezov.service.impl.ContentServiceImpl;
import com.nedorezov.service.impl.GameServiceImpl;
import com.nedorezov.service.impl.JsonParseServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;


public class GameServlet extends HttpServlet {
    private GameService gameService;


    @Override
    public void init() throws ServletException {
        super.init();
        newGame();
    }

    private void newGame() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonParseService jsonParseService = new JsonParseServiceImpl(objectMapper);
        ContentService contentService = new ContentServiceImpl(jsonParseService);
        gameService = new GameServiceImpl(contentService);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextQuestion = gameService.getNextQuestion().code;
        request.setAttribute("message", nextQuestion);
        List<String> options = gameService.getCurrentNextContent();
        request.setAttribute("options", options);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String answer = request.getParameter("answer");
        if ("true".equals(request.getParameter("reset"))) {
            newGame();
            answer = null;
        }

        if (answer != null) {
            gameService.processAnswer(answer);
            String nextQuestionOrResult = gameService.getNextQuestion().value;
            request.setAttribute("message", nextQuestionOrResult);
            List<String> options = gameService.getCurrentNextContent();
            request.setAttribute("options", options);

            if (gameService.getNextQuestion().isGameOver) {
                request.setAttribute("showRestartButton", true);
                if (gameService.getNextQuestion().isVictory()) {
                    gameService.resetGame();
                } else {
                    gameService.resetGame();
                }
            }
        }
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

}
