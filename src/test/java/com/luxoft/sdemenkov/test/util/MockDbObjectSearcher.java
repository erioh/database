package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.database.entity.CommonDbObject;
import com.luxoft.sdemenkov.database.database.entity.schema.impl.dir.SchemaDir;
import com.luxoft.sdemenkov.database.database.entity.table.impl.xml.TableXml;
import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.db.api.TargetType;

import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.TARGET_NAME;

public class MockDbObjectSearcher extends DbObjectSearcher {
    @Override
    public CommonDbObject findDbObject(TargetType targetType, Map<String, String> requestParametersMap) {
        if (targetType ==TargetType.SCHEMA){
            return new SchemaDir("dbTest/schema");
        }
        if (targetType == TargetType.TABLE){
            String targetName = requestParametersMap.get(TARGET_NAME);
            return new TableXml(targetName,requestParametersMap);
        }
        return null;
    }
}
