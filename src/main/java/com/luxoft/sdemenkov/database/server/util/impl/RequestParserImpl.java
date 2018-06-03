package com.luxoft.sdemenkov.database.server.util.impl;

import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.luxoft.sdemenkov.database.common.AdditionalRequestParameters.COLUMNS;
import static com.luxoft.sdemenkov.database.common.AdditionalRequestParameters.TARGET_NAME;

public class RequestParserImpl implements com.luxoft.sdemenkov.database.server.util.RequestParser {

    @Override
    public Request parse(BufferedReader socketReader) {
        try {
            String commandTypeValue = socketReader.readLine();
            check(socketReader);
            String targetTypeValue = socketReader.readLine();
            String parameter;
            Map<String, String> requestParametersMap = new HashMap<>();
            check(socketReader);
            while (!"end".equalsIgnoreCase(parameter = socketReader.readLine())) {
                String[] keyValue = parameter.split(":");
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.equals(TARGET_NAME) || (key.equals(COLUMNS))) {
                    value = value.toUpperCase();
                }
                requestParametersMap.put(key, value);
                check(socketReader);
            }
            CommandType commandType = CommandType.getByName(commandTypeValue.toUpperCase());
            TargetType targetType = TargetType.getByName(targetTypeValue.toUpperCase());
            return new Request(commandType, targetType, requestParametersMap);
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
