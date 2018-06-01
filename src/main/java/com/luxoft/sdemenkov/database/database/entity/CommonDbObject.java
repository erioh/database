package com.luxoft.sdemenkov.database.database.entity;

import java.util.List;

public interface CommonDbObject {
    boolean create();
    boolean drop();
    List<String> show();
    boolean exists();
}
