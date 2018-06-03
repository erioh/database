package com.luxoft.sdemenkov.database.database.util;

import com.luxoft.sdemenkov.database.database.entity.CommonDbObject;
import com.luxoft.sdemenkov.database.database.factory.DbFactory;
import com.luxoft.sdemenkov.db.api.TargetType;

import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.TARGET_NAME;


public class DbObjectSearcher {
    private DbFactory dbFactory;
    private String rootResourceFolder;

    public CommonDbObject findDbObject(TargetType targetType, Map<String, String> requestParametersMap) {
        String targetName =  requestParametersMap.get(TARGET_NAME);
        if (targetName == null) {
            throw new RuntimeException("TargetName is not provided");
        }
        targetName = rootResourceFolder + targetName;
        if (targetType == TargetType.SCHEMA) {
            return dbFactory.getSchemaByName(targetName);
        }
        if (targetType == TargetType.TABLE) {
            return dbFactory.getTableByName(targetName, requestParametersMap);
        }
        throw new RuntimeException("findDbObject doesn't know about TargetType " + targetType);
    }

    public void setRootResourceFolder(String rootResourceFolder) {
        this.rootResourceFolder = rootResourceFolder;
    }

    public void setDbFactory(DbFactory dbFactory) {
        this.dbFactory = dbFactory;
    }
}
