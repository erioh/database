package com.luxoft.sdemenkov.database.database.factory.impl.xml;

import com.luxoft.sdemenkov.database.database.entity.schema.Schema;
import com.luxoft.sdemenkov.database.database.entity.schema.impl.dir.SchemaDir;
import com.luxoft.sdemenkov.database.database.entity.table.Table;
import com.luxoft.sdemenkov.database.database.entity.table.impl.xml.TableXml;
import com.luxoft.sdemenkov.database.database.factory.DbFactory;

import java.util.Map;

public class DbFactoryXml implements DbFactory {
    @Override
    public Schema getSchemaByName(String name) {
        return new SchemaDir(name);
    }

    @Override
    public Table getTableByName(String name, Map<String, String> parameters) {
        return new TableXml(name,parameters);
    }


}
