package com.luxoft.sdemenkov.database.database.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectResult {
    private final List<Row> rowList = new ArrayList<>();
    public void addRow(Row row){
        rowList.add(row);
    }

    public List<Row> getRowList() {
        return rowList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectResult that = (SelectResult) o;
        return Objects.equals(rowList, that.rowList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rowList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SelectResult{");
        sb.append("rowList=").append(rowList);
        sb.append('}');
        return sb.toString();
    }
}
