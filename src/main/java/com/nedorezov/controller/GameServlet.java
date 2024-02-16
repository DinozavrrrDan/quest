package com.nedorezov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedorezov.service.ContentService;
import com.nedorezov.service.GameService;
import com.nedorezov.service.JsonParseService;
import com.nedorezov.service.impl.ContentServiceImpl;
import com.nedorezov.service.impl.GameServiceImpl;
import com.nedorezov.service.impl.JsonParseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static com.nedorezov.consts.WebConsts.*;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        newGame();
        String nextQuestion = gameService.getNextQuestion().code;
        request.setAttribute(MESSAGE, nextQuestion);
        List<String> options = gameService.getCurrentNextContent();
        request.setAttribute(OPTIONS, options);
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String answer = request.getParameter(ANSWER);
        if (TRUE.equals(request.getParameter(RESET))) {
            newGame();
            answer = null;
        }

        if (answer != null) {
            gameService.processAnswer(answer);
            String nextQuestionOrResult = gameService.getNextQuestion().value;
            request.setAttribute(MESSAGE, nextQuestionOrResult);
            List<String> options = gameService.getCurrentNextContent();
            request.setAttribute(OPTIONS, options);

            if (gameService.getNextQuestion().isGameOver) {
                request.setAttribute(SHOW_RESTART_BUTTON, true);
                if (gameService.getNextQuestion().isVictory()) {
                    gameService.resetGame();
                } else {
                    gameService.resetGame();
                }
            }
        }
        request.getRequestDispatcher(GAME_JSP).forward(request, response);
    }

}
