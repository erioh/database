package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;
import com.luxoft.sdemenkov.database.server.util.RequestParser;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class MockRequestParser implements RequestParser {
    @Override
    public Request parse(BufferedReader socketReader) {
        Map<String, String> map = new HashMap<>();
        map.put("TargetName", "name");
        return new Request(CommandType.SHOW, TargetType.SCHEMA, map);
    }
}
