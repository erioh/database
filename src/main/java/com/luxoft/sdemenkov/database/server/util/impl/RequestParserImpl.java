package com.luxoft.sdemenkov.database.server.util.impl;

import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParserImpl implements com.luxoft.sdemenkov.database.server.util.RequestParser {

    @Override
    public Request parse(BufferedReader socketReader) {
        try {
            String commandTypeValue = socketReader.readLine();
            String targetTypeValue = socketReader.readLine();
            String value;
            Map<String, String> requestParametersMap = new HashMap<>();
            while (!"end".equalsIgnoreCase(value = socketReader.readLine())) {
                String[] keyValue = value.split(":");
                requestParametersMap.put(keyValue[0], keyValue[1]);
            }
            CommandType commandType = CommandType.getByName(commandTypeValue);
            TargetType targetType = TargetType.getByName(targetTypeValue);
            return new Request(commandType, targetType, requestParametersMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
