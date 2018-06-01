package com.luxoft.sdemenkov.database.database.entity;

public interface Table extends CommonDbObject {
    boolean insert();
    SelectResult select();
}
