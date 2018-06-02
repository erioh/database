package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.database.entity.CommonDbObject;
import com.luxoft.sdemenkov.database.database.entity.SelectResult;
import com.luxoft.sdemenkov.database.database.entity.Table;
import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RequestExecutor {
    private DbObjectSearcher dbObjectSearcher;
    private ResponseWriter responseWriter;

    public void execute(Request request) {
        try {
            TargetType targetType = request.getTargetType();
            Map<String, String> requestParametersMap = request.getRequestParametersMap();
            CommonDbObject dbObject = dbObjectSearcher.findDbObject(
                    targetType, requestParametersMap);
            CommandType commandType = request.getCommandType();
            if (commandType == CommandType.SHOW) {
                List<String> show = dbObject.show();
                responseWriter.write(show);
            }
            if (commandType == CommandType.CREATE) {
                boolean isCreated = dbObject.create();
                responseWriter.write(isCreated ? "Object is created" : "Object is not created");
            }
            if (commandType == CommandType.DROP) {
                boolean isDropped = dbObject.drop();
                responseWriter.write(isDropped ? "Object is dropped" : "Object is not dropped");
            }
            if (commandType == CommandType.INSERT) {
                if (dbObject instanceof Table) {
                    boolean isInserted = ((Table) dbObject).insert();
                    responseWriter.write(isInserted ? "row is inserted" : "row is not inserted");
                } else {
                    throw new RuntimeException("INSERT can be applied only to TABLE");
                }
            }
            if (commandType == CommandType.SELECT) {
                if (dbObject instanceof Table) {
                    SelectResult result = ((Table) dbObject).select();
                    responseWriter.write(result);
                } else {
                    throw new RuntimeException("SELECT can be applied only to TABLE");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setDbObjectSearcher(DbObjectSearcher dbObjectSearcher) {
        this.dbObjectSearcher = dbObjectSearcher;
    }

    public void setResponseWriter(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }
}
