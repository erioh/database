package com.luxoft.sdemenkov.database.database.entity.table.impl.xml;

import com.luxoft.sdemenkov.db.api.Column;
import com.luxoft.sdemenkov.db.api.Row;
import com.luxoft.sdemenkov.db.api.SelectResult;
import com.luxoft.sdemenkov.database.database.entity.table.Table;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.COLUMNS;
import static com.luxoft.sdemenkov.db.api.AdditionalRequestParameters.COLUMNS_VALUES;


public class TableXml implements Table {
    private static final String TABLE_ROOT = "table";
    private static final String ROW = "row";
    private final Map<String, String> parameters;
    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private final File metadataFile;
    private final File tableFile;

    public TableXml(String name, Map<String, String> parameters) {
        metadataFile = new File(name + "_meta.xml");
        tableFile = new File(name+".xml");
        this.parameters = parameters;
    }

    @Override
    public boolean create() {
        if (exists()){
            throw new RuntimeException("Table "+tableFile.getName()+" is already created");
        }
        return createMetadata() && createTable();
    }

    public boolean createMetadata() {

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element table = document.createElement(TABLE_ROOT);
            document.appendChild(table);

            for (String name : parseColumns()) {
                Element column = document.createElement(name);
                table.appendChild(column);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(metadataFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            return false;
        }
        return true;
    }

    private String[] parseColumns() {
        String columnsParameter = parameters.get(COLUMNS);
        if (columnsParameter == null) {
            throw new RuntimeException("Columns are not provided");
        }
        return columnsParameter.split(",");
    }

    private String[] parseColumnsValues() {
        String columnsParameter = parameters.get(COLUMNS_VALUES);
        if (columnsParameter == null) {
            throw new RuntimeException("ColumnsValues are not provided");
        }
        return columnsParameter.split(",");
    }

    public boolean createTable() {
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element table = document.createElement("table");
            document.appendChild(table);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(tableFile);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            return false;
        }
        return true;

    }

    @Override
    public boolean drop() {
        return dropMetadata() && dropTable();
    }

    public boolean dropTable() {
        return tableFile.delete();
    }

    public boolean dropMetadata() {
        return metadataFile.delete();
    }

    @Override
    public List<String> show() {
        try {
            if (!metadataFile.exists()) {
                throw new RuntimeException("Metadata for table " + tableFile.getName() + " doesn't exist");
            }
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(metadataFile);
            Element documentElement = document.getDocumentElement();
            List<String> listOfNodes = new ArrayList<>();
            NodeList childNodes = documentElement.getChildNodes();
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                String nodeName = childNodes.item(i).getNodeName();
                listOfNodes.add(nodeName);
            }
            return listOfNodes;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists() {
        return metadataFile.exists() && tableFile.exists();
    }

    @Override
    public boolean insert() {
        if (!exists()) {
            throw new RuntimeException("Table doesn't exists");
        }
        List<String> nodeList = show();
        String[] columns = parseColumns();
        String[] columnsValues = parseColumnsValues();
        if (columns.length != columnsValues.length) {
            throw new RuntimeException("incorrect number of values. " + columns.length + " are expected, but " + columnsValues.length + " were received.");
        }
        for (String column : columns) {
            if (!nodeList.contains(column)) {
                throw new RuntimeException("Column " + column + " doesn't exists in table " + tableFile.getName());
            }
        }
        if (columns.length != nodeList.size()){
            throw new RuntimeException("not all collumns are provided");
        }

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(tableFile);
            Element tableNode = document.getDocumentElement();
            Element rowNode = document.createElement(ROW);
            tableNode.appendChild(rowNode);
            for (int i = 0; i < columns.length; i++) {
                Element element = document.createElement(columns[i]);
                element.setTextContent(columnsValues[i]);
                rowNode.appendChild(element);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(tableFile);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public SelectResult select() {
        SelectResult result = new SelectResult();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(tableFile);
            Element tableNode = document.getDocumentElement();
            NodeList rowNodeList = tableNode.getChildNodes();
            for (int i = 0; i < rowNodeList.getLength(); i++) {
                Node rowNode = rowNodeList.item(i);
                Row row = new Row();
                result.addRow(row);
                NodeList columnNodeList = rowNode.getChildNodes();
                for (int j = 0; j < columnNodeList.getLength(); j++) {
                    Node columnNode = columnNodeList.item(j);
                    String nodeName = columnNode.getNodeName();
                    String nodeValue = columnNode.getTextContent();
                    Column column = new Column(nodeName, nodeValue);
                    row.addColumn(column);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Unknown Object");
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableXml{");
        sb.append("name='").append(tableFile.getName()).append('\'');
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableXml tableXml = (TableXml) o;
        return Objects.equals(parameters, tableXml.parameters) &&
                Objects.equals(metadataFile.getName(), tableXml.metadataFile.getName()) &&
                Objects.equals(tableFile.getName(), tableXml.tableFile.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(parameters, metadataFile.getName(), tableFile.getName());
    }
}
