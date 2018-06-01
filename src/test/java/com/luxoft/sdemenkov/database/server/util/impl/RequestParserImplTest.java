package com.luxoft.sdemenkov.database.server.util.impl;

import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;
import com.luxoft.sdemenkov.database.server.util.RequestParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class RequestParserImplTest {
    private RequestParser requestParser;
    private BufferedReader validReader;
    private Request validRequest;

    @Test
    public void parse() {

        Request validParsedRequest = requestParser.parse(validReader);
        assertEquals(validRequest,validParsedRequest);
    }

    @Before
    public void setUp() {
        requestParser = new RequestParserImpl();
        String validString = "CREATE\r\nTABLE\r\nTargetName:NEW_TABLE\r\nend\r\n";
        ByteArrayInputStream validInputStream = new ByteArrayInputStream(validString.getBytes());
        validReader = new BufferedReader(new InputStreamReader(validInputStream));
        Map<String, String> parameters = new HashMap<>();
        parameters.put("TargetName","NEW_TABLE");
        validRequest = new Request(CommandType.CREATE, TargetType.TABLE,parameters);


    }

    @After
    public void tearDown() throws Exception {
        validReader.close();
    }
}