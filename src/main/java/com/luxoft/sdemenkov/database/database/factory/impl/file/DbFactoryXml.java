package com.luxoft.sdemenkov.database.database.factory.impl.file;

import com.luxoft.sdemenkov.database.database.entity.Table;
import com.luxoft.sdemenkov.database.database.entity.Schema;
import com.luxoft.sdemenkov.database.database.entity.impl.xml.SchemaXml;
import com.luxoft.sdemenkov.database.database.entity.impl.xml.TableXml;
import com.luxoft.sdemenkov.database.database.factory.DbFactory;

import java.util.Map;

public class DbFactoryXml implements DbFactory {
    @Override
    public Schema getSchemaByName(String name) {
        return new SchemaXml(name);
    }

    @Override
    public Table getTableByName(String name, Map<String, String> parameters) {
        return new TableXml(name,parameters);
    }


}
