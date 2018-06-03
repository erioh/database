package com.luxoft.sdemenkov.database.server.util.impl;

import com.luxoft.sdemenkov.database.server.util.RequestParser;
import com.luxoft.sdemenkov.db.api.CommandType;
import com.luxoft.sdemenkov.db.api.Request;
import com.luxoft.sdemenkov.db.api.TargetType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.TARGET_NAME;
import static junit.framework.TestCase.assertEquals;

public class RequestParserJsonTest {
    private RequestParser requestParser;
    private BufferedReader validReader;
    private Request validRequest;

    @Test
    public void parse() {

        Request validParsedRequest = requestParser.parse(validReader);
        assertEquals(validRequest, validParsedRequest);
    }

    @Before
    public void setUp() {
        requestParser = new RequestParserJson();
        String validString = "{\"commandType\":\"CREATE\",\"targetType\":\"TABLE\",\"requestParametersMap\":{\"TargetName\":\"NEW_TABLE\"}}\r\nend";
        ByteArrayInputStream validInputStream = new ByteArrayInputStream(validString.getBytes());
        validReader = new BufferedReader(new InputStreamReader(validInputStream));
        Map<String, String> parameters = new HashMap<>();
        parameters.put(TARGET_NAME, "NEW_TABLE");
        validRequest = new Request(CommandType.CREATE, TargetType.TABLE, parameters);
    }

    @After
    public void tearDown() throws Exception {
        validReader.close();
    }
}