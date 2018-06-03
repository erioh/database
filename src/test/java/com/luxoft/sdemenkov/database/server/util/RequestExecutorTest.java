package com.luxoft.sdemenkov.database.server.util;

import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.db.api.Request;
import com.luxoft.sdemenkov.test.util.MockDbObjectSearcher;
import com.luxoft.sdemenkov.test.util.RequestCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.assertTrue;

public class RequestExecutorTest {
    private RequestExecutor requestExecutor;
    private BufferedWriter socketWriter;
    private File root;
    File file;
    File meta;

    @Test
    public void execute() {
        Request request = RequestCreator.getCreateTableRequest();
        requestExecutor.execute(request);
        file = new File("dbTest/NEW_TABLE.xml");
        meta = new File("dbTest/NEW_TABLE_meta.xml");
        assertTrue(file.exists());
    }

    @Before
    public void setUp() {
        root = new File("dbTest");
        root.mkdir();
        requestExecutor = new RequestExecutor();
        OutputStream outputStream = new ByteArrayOutputStream();
        socketWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        ResponseWriter responseWriter = new ResponseWriter();
        responseWriter.setWriter(socketWriter);
        DbObjectSearcher dbObjectSearcher = new MockDbObjectSearcher();
        requestExecutor.setDbObjectSearcher(dbObjectSearcher);
    }

    @After
    public void tearDown() throws Exception {
        file.delete();
        meta.delete();
        root.delete();
        socketWriter.close();
    }
}