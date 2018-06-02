package com.luxoft.sdemenkov.database.database.factory;

import com.luxoft.sdemenkov.database.database.entity.schema.Schema;
import com.luxoft.sdemenkov.database.database.entity.table.Table;

import java.util.Map;

public interface DbFactory {
    Schema getSchemaByName(String name);
    Table getTableByName(String name, Map<String,String> parameters);
}
