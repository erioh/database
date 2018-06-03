package com.luxoft.sdemenkov.database.database.entity.table;

import com.luxoft.sdemenkov.database.database.entity.CommonDbObject;
import com.luxoft.sdemenkov.db.api.SelectResult;

public interface Table extends CommonDbObject {
    boolean insert();
    SelectResult select();
}
