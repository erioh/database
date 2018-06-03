package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.server.entity.CommandType;
import com.luxoft.sdemenkov.database.server.entity.Request;
import com.luxoft.sdemenkov.database.server.entity.TargetType;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.sdemenkov.database.common.AdditionalRequestParameters.COLUMNS;
import static com.luxoft.sdemenkov.database.common.AdditionalRequestParameters.TARGET_NAME;

public class RequestCreator {
    public static Request getCreateTableRequest() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put(TARGET_NAME,"dbTest/NEW_TABLE");
        parameters.put(COLUMNS,"column1,column2");
        return new Request(CommandType.CREATE, TargetType.TABLE,parameters);
    }
}
