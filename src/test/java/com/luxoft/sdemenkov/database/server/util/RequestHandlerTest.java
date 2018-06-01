package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.test.util.MockRequestExecutor;
import com.luxoft.sdemenkov.test.util.MockRequestParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class RequestHandlerTest {
    private RequestHandler handler;
    private BufferedReader reader;
    private BufferedWriter writer;
    private InputStream inputStream;
    private OutputStream outputStream;
    private RequestExecutor requestExecutor;

    @Test
    public void handle() {
        handler.handle();
    }

    @Before
    public void setUp() {
        byte[] bytes = "some value".getBytes();
        inputStream = new ByteArrayInputStream(bytes);
        outputStream = new ByteArrayOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        RequestParser requestParser = new MockRequestParser();
        requestExecutor = new MockRequestExecutor();
        handler = new RequestHandler();
        handler.setSocketReader(reader);
        handler.setSocketWriter(writer);
        handler.setRequestParser(requestParser);
        handler.setRequestExecutor(requestExecutor);

    }

    @After
    public void tearDown() throws Exception {
        reader.close();
        writer.close();
        inputStream.close();
        outputStream.close();
    }


}