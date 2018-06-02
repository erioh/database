package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.server.entity.Response;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {
    private BufferedWriter writer;

    public void write(Object object) throws IOException {
        Response response = new Response(object);
        writer.write(response.toString());
        writer.write("\r\n");
        writer.write("\r\n");
        writer.flush();
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }
}
