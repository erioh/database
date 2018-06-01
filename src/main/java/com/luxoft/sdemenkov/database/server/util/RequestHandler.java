package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.server.entity.Request;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class RequestHandler {
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private RequestParser requestParser;
    private RequestExecutor requestExecutor;

    public void handle() {
        Request request = requestParser.parse(socketReader);
        requestExecutor.setSocketWriter(socketWriter);
        requestExecutor.execute(request);
    }

    public void setSocketReader(BufferedReader socketReader) {
        this.socketReader = socketReader;
    }

    public void setRequestExecutor(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void setSocketWriter(BufferedWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public void setRequestParser(RequestParser requestParser) {
        this.requestParser = requestParser;
    }
}