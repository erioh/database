package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;

import java.util.HashMap;
import java.util.Map;

public class RequestCreator {
    public static Request getCreateTableRequest() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("TargetName","dbTest/NEW_TABLE");
        parameters.put("Columns","column1,column2");
        return new Request(CommandType.CREATE, TargetType.TABLE,parameters);
    }
}
