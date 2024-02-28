package com.nedorezov.service.impl;

import com.nedorezov.exception.JsonParseException;
import com.nedorezov.model.GameData;
import com.nedorezov.service.ContentService;
import com.nedorezov.service.JsonParseService;

import java.io.IOException;
import java.io.InputStream;

import static com.nedorezov.consts.WebConsts.CANNOT_PARSE_STRING_TO_OBJECT;
import static com.nedorezov.consts.WebConsts.GAME_JSON;


public class ContentServiceImpl implements ContentService {

    private final JsonParseService jsonParseService;

    public ContentServiceImpl(JsonParseService jsonParseService) {
        this.jsonParseService = jsonParseService;
    }

    @Override
    public GameData initRoot() {
        GameData gameData = null;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(GAME_JSON)) {
            gameData = (GameData) jsonParseService.readObject(inputStream, GameData.class);
        } catch (IOException | JsonParseException e) {
            System.out.println(CANNOT_PARSE_STRING_TO_OBJECT);
            e.printStackTrace();
        }
        return gameData;
    }
}
