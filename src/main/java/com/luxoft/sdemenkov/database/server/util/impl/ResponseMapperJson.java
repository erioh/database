package com.luxoft.sdemenkov.database.server.util.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.sdemenkov.database.exception.SocketRuntimeException;
import com.luxoft.sdemenkov.database.server.util.ResponseMapper;
import com.luxoft.sdemenkov.db.api.Response;

public class ResponseMapperJson implements ResponseMapper {
    @Override
    public String map(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new SocketRuntimeException(e);
        }
    }
}
