package com.luxoft.sdemenkov.database.server;

import com.luxoft.sdemenkov.database.database.factory.DbFactory;
import com.luxoft.sdemenkov.database.database.factory.impl.file.DbFactoryXml;
import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.database.server.util.RequestExecutor;
import com.luxoft.sdemenkov.database.server.util.RequestHandler;
import com.luxoft.sdemenkov.database.server.util.RequestParser;
import com.luxoft.sdemenkov.database.server.util.impl.RequestParserImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 3000;
    private String rootResourceFolder;
    private RequestHandler requestHandler;

    public void start() {
        requestHandler = new RequestHandler();
        configure();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                ) {
                    try {
                        requestHandler.setSocketReader(socketReader);
                        requestHandler.setSocketWriter(socketWriter);
                        requestHandler.handle();
                    } catch (RuntimeException e) {
                        socketWriter.write(e.getMessage());
                        socketWriter.flush();
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    public void configure() {
        DbObjectSearcher searcher = new DbObjectSearcher();
        DbFactory factory = new DbFactoryXml();
        RequestParser parser = new RequestParserImpl();
        RequestExecutor executor = new RequestExecutor();

        searcher.setDbFactory(factory);
        searcher.setRootResourceFolder(rootResourceFolder);

        executor.setDbObjectSearcher(searcher);

        requestHandler.setRequestParser(parser);
        requestHandler.setRequestExecutor(executor);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRootResourceFolder(String rootResourceFolder) {
        this.rootResourceFolder = rootResourceFolder;
    }
}