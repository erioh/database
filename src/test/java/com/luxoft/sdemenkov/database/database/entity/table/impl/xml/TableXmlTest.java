package com.luxoft.sdemenkov.database.database.entity.table.impl.xml;

import com.luxoft.sdemenkov.database.database.entity.table.Table;
import com.luxoft.sdemenkov.db.api.Row;
import com.luxoft.sdemenkov.db.api.SelectResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.COLUMNS;
import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.COLUMNS_VALUES;
import static junit.framework.TestCase.*;

public class TableXmlTest {
    private final String expectedBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><table/>";
    private final String expectedBodyAfterInsert = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><table><row><column1>value 1</column1><column2>second value</column2></row><row><column1>value 1</column1><column2>second value</column2></row><row><column1>value 1</column1><column2>second value</column2></row></table>";
    private final String expectedBodyWithData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><table><row><column1>row 1 value 1</column1><column2>row 1second value</column2></row><row><column1>row 2 value 1</column1><column2>row 2 second value</column2></row><row><column1>row 3 value 1</column1><column2>row 3 second value</column2></row></table>";
    private final String expectedMetadata ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><table><column1/><column2/></table>";
    private File root;
    private File file;
    private File fileMetadata;
    private File existedFile;
    private File existedFileMeta;
    private File existedFileWithData;
    private File existedFileMetaWithData;
    @Before
    public void setUp() throws Exception {
        root = new File("testDb");
        root.mkdir();
        file = new File("testDb/new_table.xml");
        fileMetadata = new File("testDb/new_table_meta.xml");
        existedFile = new File("testDb/old_table.xml");
        existedFileMetaWithData = new File("testDb/data_table_meta.xml");
        existedFileWithData = new File("testDb/data_table.xml");
        existedFile.createNewFile();
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(existedFile));
        fileWriter.write(expectedBody);
        fileWriter.close();
        existedFileMeta = new File("testDb/old_table_meta.xml");
        existedFileMeta.createNewFile();
        BufferedWriter metadataWriter = new BufferedWriter(new FileWriter(existedFileMeta));
        metadataWriter.write(expectedMetadata);
        metadataWriter.close();

        BufferedWriter fileWithDataWriter = new BufferedWriter(new FileWriter(existedFileWithData));
        fileWithDataWriter.write(expectedBodyWithData);
        fileWithDataWriter.close();
        BufferedWriter metadataWithDataWriter = new BufferedWriter(new FileWriter(existedFileMetaWithData));
        metadataWithDataWriter.write(expectedMetadata);
        metadataWithDataWriter.close();
    }

    @After
    public void tearDown() {
        file.delete();
        fileMetadata.delete();
        existedFile.delete();
        existedFileMeta.delete();
        existedFileWithData.delete();
        existedFileMetaWithData.delete();
        root.delete();
    }

    @Test
    public void create() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        Table table = new TableXml("testDb/new_table", parameters);
        assertTrue(table.create());
        assertTrue(file.exists());
        assertTrue(fileMetadata.exists());
        BufferedReader bodyReader = new BufferedReader(new FileReader(file));
        BufferedReader metadataReader = new BufferedReader(new FileReader(fileMetadata));
        assertEquals(expectedBody, bodyReader.readLine());
        assertEquals(expectedMetadata, metadataReader.readLine());
    }

    @Test
    public void drop() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        Table table = new TableXml("testDb/old_table", parameters);
        table.drop();
        assertFalse(existedFile.exists());
        assertFalse(existedFileMeta.exists());
    }

    @Test
    public void show() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        Table table = new TableXml("testDb/old_table", parameters);
        List<String> nodeList = table.show();
        assertEquals(2, nodeList.size());
        assertFalse(nodeList.contains("table"));
        assertTrue(nodeList.contains("column1"));
        assertTrue(nodeList.contains("column2"));
    }

    @Test
    public void exists() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        Table table = new TableXml("testDb/old_table", parameters);
        assertTrue(table.exists());
    }

    @Test
    public void existsNot() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        Table table = new TableXml("testDb/new_table", parameters);
        assertFalse(table.exists());
    }
    @Test
    public void existsWithoutMetadata() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        Table table = new TableXml("testDb/old_table", parameters);
        existedFileMeta.delete();
        assertFalse(table.exists());
    }

    @Test
    public void insert() throws IOException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(COLUMNS,"column1,column2");
        parameters.put(COLUMNS_VALUES,"value 1,second value");
        Table table = new TableXml("testDb/old_table", parameters);
        table.insert();
        table.insert();
        table.insert();
        BufferedReader reader = new BufferedReader(new FileReader(existedFile));
        assertEquals(expectedBodyAfterInsert, reader.readLine());
    }

    @Test
    public void select() {
        String expected = "[Row{columnList=[Column{columnName='column1', columnValue='row 1 value 1'}, Column{columnName='column2', columnValue='row 1second value'}]}, Row{columnList=[Column{columnName='column1', columnValue='row 2 value 1'}, Column{columnName='column2', columnValue='row 2 second value'}]}, Row{columnList=[Column{columnName='column1', columnValue='row 3 value 1'}, Column{columnName='column2', columnValue='row 3 second value'}]}]";
        Map<String, String> parameters = new HashMap<>();
        Table table = new TableXml("testDb/data_table", parameters);
        SelectResult result = table.select();
        List<Row> rowList = result.getRowList();
        assertEquals(expected, rowList.toString());

    }
}