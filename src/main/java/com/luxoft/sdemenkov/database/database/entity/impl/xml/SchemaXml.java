package com.luxoft.sdemenkov.database.database.entity.impl.xml;


import com.luxoft.sdemenkov.database.database.entity.Schema;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SchemaXml implements Schema {
    private final String name;
    private File file;

    public SchemaXml(String name) {
        this.name = name;
        file = new File(name);

    }

    @Override
    public boolean create() {
        if (!file.exists()) {
            return file.mkdir();
        } else {
            throw new RuntimeException("DB Object with name " + name + " is already created");
        }
    }

    @Override
    public boolean drop() {
        if (!file.exists()) {
            throw new RuntimeException("DB Object with name  " + name + " doesn't exists");
        }
        return file.delete();
    }

    @Override
    public List<String> show() {
        String[] list = file.list((file, name) -> !name.toLowerCase().endsWith("_meta.xml"));
        List<String> tableList = new ArrayList<>();
        if (list != null) {
            Collections.addAll(tableList, list);
        }
        return tableList;
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SchemaXml{");
        sb.append("name='").append(name).append('\'');
        sb.append(", file=").append(file);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaXml that = (SchemaXml) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, file);
    }
}
