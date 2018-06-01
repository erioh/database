package com.luxoft.sdemenkov.database.database.entity.impl.xml;

import com.luxoft.sdemenkov.database.database.entity.Schema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SchemaXmlTest {
    private final String schemaForDelete = "testDb/schema_for_delete";
    private final String schemaForCreate = "testDb/new_schema";
    private final String schemaWithTables = "testDb/schema_with_tables";
    private final String root = "testDb";

    @Before
    public void setUp() throws IOException {
        Files.createDirectory(Paths.get(root));
        Files.createDirectory(Paths.get(schemaForDelete));
        Files.createDirectory(Paths.get(schemaWithTables));
        Files.createFile(Paths.get(schemaWithTables+"/"+"table1.xml"));
        Files.createFile(Paths.get(schemaWithTables+"/"+"table2.xml"));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(schemaForCreate));
        Files.deleteIfExists(Paths.get(schemaForDelete));
        Files.deleteIfExists(Paths.get(schemaWithTables+"/"+"table1.xml"));
        Files.deleteIfExists(Paths.get(schemaWithTables+"/"+"table2.xml"));
        Files.deleteIfExists(Paths.get(schemaWithTables));
        Files.deleteIfExists(Paths.get(root));
    }

    @Test
    public void create() {
        Schema schema = new SchemaXml(schemaForCreate);
        assertTrue(schema.create());
    }

    @Test
    public void drop() {
        Schema schema = new SchemaXml(schemaForDelete);
        assertTrue(schema.drop());
    }

    @Test
    public void show() {
        Schema schema = new SchemaXml(schemaWithTables);
        List<String> tables = schema.show();
        assertEquals(2,tables.size());
        assertTrue(tables.contains("table1.xml"));
        assertTrue(tables.contains("table2.xml"));

    }
}