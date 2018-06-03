package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.server.util.RequestExecutor;
import com.luxoft.sdemenkov.db.api.Request;
import com.luxoft.sdemenkov.db.api.Response;

public class MockRequestExecutor extends RequestExecutor {

    @Override
    public Response execute(Request request) {
        return new Response();
    }
}
