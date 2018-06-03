package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.db.api.CommandType;
import com.luxoft.sdemenkov.db.api.Request;
import com.luxoft.sdemenkov.db.api.TargetType;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.COLUMNS;
import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.TARGET_NAME;

public class RequestCreator {
    public static Request getCreateTableRequest() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put(TARGET_NAME,"dbTest/NEW_TABLE");
        parameters.put(COLUMNS,"column1,column2");
        return new Request(CommandType.CREATE, TargetType.TABLE,parameters);
    }
}
