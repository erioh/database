package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.database.entity.CommonDbObject;
import com.luxoft.sdemenkov.database.database.entity.Schema;
import com.luxoft.sdemenkov.database.database.entity.Table;
import com.luxoft.sdemenkov.database.database.entity.impl.xml.SchemaXml;
import com.luxoft.sdemenkov.database.database.entity.impl.xml.TableXml;
import com.luxoft.sdemenkov.database.database.factory.DbFactory;
import com.luxoft.sdemenkov.database.database.factory.impl.file.DbFactoryXml;
import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.database.server.entity.TargetType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DbObjectSearcherTest {
    private DbFactory dbFactory;
    private DbObjectSearcher objectSearcher;

    private final String schema = "testDb/schema";
    private final String schemaShort = "/schema";
    private final String root = "testDb";

    @Before
    public void setUp() throws IOException {
        dbFactory = new DbFactoryXml();
        objectSearcher = new DbObjectSearcher();
        objectSearcher.setDbFactory(dbFactory);
        objectSearcher.setRootResourceFolder(root);
        Files.createDirectory(Paths.get(root));
        Files.createDirectory(Paths.get(schema));
        Files.createFile(Paths.get(schema+"/"+"table1.xml"));
        Files.createFile(Paths.get(schema+"/"+"table1_meta.xml"));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(schema+"/"+"table1.xml"));
        Files.deleteIfExists(Paths.get(schema+"/"+"table1_meta.xml"));
        Files.deleteIfExists(Paths.get(schema));
        Files.deleteIfExists(Paths.get(root));
    }


    @Test
    public void findExistedSchema() {
        Schema expected = new SchemaXml(schema);
        Map<String, String> map = new HashMap<>();
        map.put("TargetName", schemaShort);
        CommonDbObject dbObject = objectSearcher.findDbObject(TargetType.SCHEMA, map);
        assertEquals(expected, dbObject);
        assertTrue(dbObject.exists());
    }
    @Test
    public void findNotExistedSchema() {
        Map<String, String> map = new HashMap<>();
        map.put("TargetName", "wrong");
        CommonDbObject dbObject = objectSearcher.findDbObject(TargetType.SCHEMA, map);
        assertFalse(dbObject.exists());
    }
    @Test
    public void findExistedTable() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("TargetName", schemaShort+"/table1");
        Table expected = new TableXml(schema+"/table1", parameters);
        CommonDbObject dbObject = objectSearcher.findDbObject(TargetType.TABLE, parameters);
        assertEquals(expected, dbObject);
        assertTrue(dbObject.exists());
    }
    @Test
    public void findNotExistedTable() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("TargetName", schemaShort+"/table2.xml");
        Table expected = new TableXml(schema+"/table2.xml", parameters);
        CommonDbObject dbObject = objectSearcher.findDbObject(TargetType.TABLE, parameters);
        assertEquals(expected, dbObject);
        assertFalse(dbObject.exists());
    }
}