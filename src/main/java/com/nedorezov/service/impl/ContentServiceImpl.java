package com.nedorezov.service.impl;

import com.nedorezov.exception.JsonParseException;
import com.nedorezov.model.Root;
import com.nedorezov.service.ContentService;
import com.nedorezov.service.JsonParseService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContentServiceImpl implements ContentService {

    private final JsonParseService jsonParseService;

    public ContentServiceImpl(JsonParseService jsonParseService) {
        this.jsonParseService = jsonParseService;
    }

    @Override
    public Root initRoot() {
        Root root = null;
        try (InputStream inputStream = Files.newInputStream(Path.of("resources/game.json"))) {
            root = (Root) jsonParseService.readObject(inputStream, Root.class);
        } catch (IOException | JsonParseException e) {
            System.out.println("Cannot parse string to object");
        }
        return root;
    }
}
