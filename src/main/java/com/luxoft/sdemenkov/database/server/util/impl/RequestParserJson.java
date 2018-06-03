package com.luxoft.sdemenkov.database.server.util.impl;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.sdemenkov.database.exception.SocketRuntimeException;
import com.luxoft.sdemenkov.db.api.Request;

import java.io.BufferedReader;
import java.io.IOException;


public class RequestParserJson implements com.luxoft.sdemenkov.database.server.util.RequestParser {

    @Override
    public Request parse(BufferedReader socketReader) {
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            String value;
            while (!"end".equalsIgnoreCase(value = socketReader.readLine())) {
                if (!socketReader.ready()) {
                    throw new SocketRuntimeException("Request should ends with word 'end'. Request body: " + jsonBuilder.append(value).toString());
                }
                jsonBuilder.append(value);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonBuilder.toString(), Request.class);
        } catch (JsonParseException | JsonMappingException e) {
            throw new RuntimeException("JSON is corrupted. Request: " + jsonBuilder.toString());
        } catch (IOException e) {
            throw new SocketRuntimeException(e.getMessage());
        }
    }
}
