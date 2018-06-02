package com.luxoft.sdemenkov.database.server.entity;

public class Response {
    private final Object body;

    public Response(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
