package com.luxoft.sdemenkov.database.server;

import org.junit.Ignore;
import org.junit.Test;

public class ServerTest {

    @Test
    @Ignore
    public void start() {
        Server server = new Server();
        server.setPort(3000);
        server.setRootResourceFolder("resources/folder/");
        server.start();
    }
}