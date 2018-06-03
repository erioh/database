package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.db.api.Response;
import com.luxoft.sdemenkov.db.api.SelectResult;

import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder {
    private final List<String> messageList = new ArrayList<>();
    private SelectResult result;

    private ResponseBuilder() {
    }

    public static ResponseBuilder start() {
        return new ResponseBuilder();
    }

    public void addMessage(String string) {
        messageList.add(string);
    }

    public void setResult(SelectResult result) {
        this.result = result;
    }

    public Response build() {
        Response response = new Response();
        response.setMessageList(messageList);
        response.setResult(result);
        return response;
    }

}
