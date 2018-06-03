package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.exception.SocketRuntimeException;
import com.luxoft.sdemenkov.db.api.Response;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
    private BufferedWriter writer;
    private ResponseMapper responseMapper;

    public void write(Response response) {
        try {
            String json = responseMapper.map(response);
            writer.write(json);
            writer.write("\r\n");
            writer.write("\r\n");
            writer.flush();

        } catch (IOException e) {
            throw new SocketRuntimeException(e);
        }

    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void setResponseMapper(ResponseMapper responseMapper) {
        this.responseMapper = responseMapper;
    }
}
