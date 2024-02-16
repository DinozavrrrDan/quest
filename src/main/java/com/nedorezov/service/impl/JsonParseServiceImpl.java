package com.nedorezov.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedorezov.exception.JsonParseException;
import com.nedorezov.service.JsonParseService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class JsonParseServiceImpl implements JsonParseService {
    private final ObjectMapper objectMapper;

    public JsonParseServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object readObject(InputStream json, Class object) throws JsonParseException {
        try {
            Scanner s = new Scanner(json).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            System.out.println(result);
            return objectMapper.readValue(result, object);
        } catch (IOException e) {
            System.out.println("Cannot parse string to object");
            throw new JsonParseException();
        }
    }
}
