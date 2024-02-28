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

import static com.nedorezov.consts.WebConsts.MESSAGE;
import static com.nedorezov.consts.WebConsts.OPTIONS;
import static com.nedorezov.consts.WebConsts.INDEX_JSP;
import static com.nedorezov.consts.WebConsts.GAME_JSP;
import static com.nedorezov.consts.WebConsts.TRUE;
import static com.nedorezov.consts.WebConsts.ANSWER;
import static com.nedorezov.consts.WebConsts.RESET;
import static com.nedorezov.consts.WebConsts.VICTORY;
import static com.nedorezov.consts.WebConsts.SHOW_RESTART_BUTTON;


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
        request.setAttribute(MESSAGE,  gameService.getNextQuestion().getValue());
        request.setAttribute(OPTIONS, gameService.getCurrentNextContent());
        request.getRequestDispatcher(INDEX_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(MESSAGE, gameService.getNextQuestion().getValue());
        request.setAttribute(OPTIONS, gameService.getCurrentNextContent());

        String answer = request.getParameter(ANSWER);
        if (TRUE.equals(request.getParameter(RESET))) {
            newGame();
            answer = null;
        }

        if (answer != null) {
            gameService.processAnswer(answer);
            String nextQuestionOrResult = gameService.getNextQuestion().getValue();
            request.setAttribute(MESSAGE, nextQuestionOrResult);
            List<String> options = gameService.getCurrentNextContent();
            request.setAttribute(OPTIONS, options);

            if (gameService.getNextQuestion().isGameOver()) {
                request.setAttribute(VICTORY, gameService.getNextQuestion().isVictory());

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
