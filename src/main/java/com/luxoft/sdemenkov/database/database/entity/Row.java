package com.luxoft.sdemenkov.database.database.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Row {
    private final List<Column> columnList = new ArrayList<>();
    public void addColumn(Column column){
        columnList.add(column);
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return Objects.equals(columnList, row.columnList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(columnList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Row{");
        sb.append("columnList=").append(columnList);
        sb.append('}');
        return sb.toString();
    }
}
