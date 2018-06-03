package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.database.entity.CommonDbObject;
import com.luxoft.sdemenkov.database.database.entity.table.Table;
import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.db.api.*;

import java.util.List;
import java.util.Map;

public class RequestExecutor {
    private DbObjectSearcher dbObjectSearcher;

    public Response execute(Request request) {
        ResponseBuilder responseBuilder = ResponseBuilder.start();
        TargetType targetType = request.getTargetType();
        Map<String, String> requestParametersMap = request.getRequestParametersMap();
        CommonDbObject dbObject = dbObjectSearcher.findDbObject(
                targetType, requestParametersMap);
        CommandType commandType = request.getCommandType();
        if (commandType == CommandType.SHOW) {
            List<String> show = dbObject.show();
            for (String s : show) {
                responseBuilder.addMessage(s);
            }
        }
        if (commandType == CommandType.CREATE) {
            boolean isCreated = dbObject.create();
            responseBuilder.addMessage(isCreated ? "Object is created" : "Object is not created");
        }
        if (commandType == CommandType.DROP) {
            boolean isDropped = dbObject.drop();
            responseBuilder.addMessage(isDropped ? "Object is dropped" : "Object is not dropped");
        }
        if (commandType == CommandType.INSERT) {
            if (dbObject instanceof Table) {
                boolean isInserted = ((Table) dbObject).insert();
                responseBuilder.addMessage(isInserted ? "row is inserted" : "row is not inserted");
            } else {
                throw new RuntimeException("INSERT can be applied only to TABLE");
            }
        }
        if (commandType == CommandType.SELECT) {
            if (dbObject instanceof Table) {
                SelectResult result = ((Table) dbObject).select();
                responseBuilder.setResult(result);
            } else {
                throw new RuntimeException("SELECT can be applied only to TABLE");
            }
        }
        return responseBuilder.build();
    }


    public void setDbObjectSearcher(DbObjectSearcher dbObjectSearcher) {
        this.dbObjectSearcher = dbObjectSearcher;
    }

}
