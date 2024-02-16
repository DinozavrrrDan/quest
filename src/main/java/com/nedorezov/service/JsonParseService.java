package com.nedorezov.service;


import com.nedorezov.exception.JsonParseException;

import java.io.InputStream;

public interface JsonParseService {
    public Object readObject(InputStream json, Class object) throws JsonParseException;
}
