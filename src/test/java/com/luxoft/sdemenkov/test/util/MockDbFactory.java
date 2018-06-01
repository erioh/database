package com.luxoft.sdemenkov.test.util;

import com.luxoft.sdemenkov.database.database.entity.Schema;
import com.luxoft.sdemenkov.database.database.entity.Table;
import com.luxoft.sdemenkov.database.database.factory.DbFactory;

import java.util.Map;

public class MockDbFactory implements DbFactory {
    @Override
    public Schema getSchemaByName(String name) {
        return null;
    }

    @Override
    public Table getTableByName(String name, Map<String, String> parameters) {
        return null;
    }


}
