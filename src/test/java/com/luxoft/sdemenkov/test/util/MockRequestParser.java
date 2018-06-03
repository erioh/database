package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.server.util.RequestParser;
import com.luxoft.sdemenkov.db.api.CommandType;
import com.luxoft.sdemenkov.db.api.Request;
import com.luxoft.sdemenkov.db.api.TargetType;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.TARGET_NAME;

public class MockRequestParser implements RequestParser {
    @Override
    public Request parse(BufferedReader socketReader) {
        Map<String, String> map = new HashMap<>();
        map.put(TARGET_NAME, "name");
        return new Request(CommandType.SHOW, TargetType.SCHEMA, map);
    }
}
