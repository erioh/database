package com.luxoft.sdemenkov.database.server.util.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.sdemenkov.db.api.CommandType;
import com.luxoft.sdemenkov.db.api.Request;
import com.luxoft.sdemenkov.db.api.TargetType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.COLUMNS;
import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.TARGET_NAME;


public class RequestParserJson implements com.luxoft.sdemenkov.database.server.util.RequestParser {

    @Override
    public Request parse(BufferedReader socketReader) {
        try {
            String value;
            StringBuilder jsonBuilder = new StringBuilder();
            while(!"end".equalsIgnoreCase(value=socketReader.readLine())){
                jsonBuilder.append(value);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonBuilder.toString(), Request.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void check(BufferedReader reader) throws IOException {
        if (!reader.ready()) {
            throw new RuntimeException("Request is incorrect");
        }
    }
}
