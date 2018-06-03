package com.luxoft.sdemenkov.database.server.util;


import com.luxoft.sdemenkov.db.api.Request;

import java.io.BufferedReader;

public interface RequestParser {
    Request parse(BufferedReader socketReader);
}
