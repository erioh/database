package com.luxoft.sdemenkov.database.server.util;


import com.luxoft.sdemenkov.db.api.Response;
import com.luxoft.sdemenkov.db.api.Request;

import java.io.BufferedReader;

public class RequestHandler {
    private BufferedReader socketReader;
    private RequestParser requestParser;
    private RequestExecutor requestExecutor;
    private ResponseWriter responseWriter;

    public void handle() {
        Request request = requestParser.parse(socketReader);
        Response response = requestExecutor.execute(request);
        responseWriter.write(response);
    }

    public void setSocketReader(BufferedReader socketReader) {
        this.socketReader = socketReader;
    }

    public void setRequestExecutor(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void setRequestParser(RequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }
}
