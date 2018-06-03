package com.luxoft.sdemenkov.database.server;

import com.luxoft.sdemenkov.database.database.factory.DbFactory;
import com.luxoft.sdemenkov.database.database.factory.impl.xml.DbFactoryXml;
import com.luxoft.sdemenkov.database.database.util.DbObjectSearcher;
import com.luxoft.sdemenkov.database.exception.SocketRuntimeException;
import com.luxoft.sdemenkov.database.server.util.*;
import com.luxoft.sdemenkov.database.server.util.impl.RequestParserJson;
import com.luxoft.sdemenkov.database.server.util.ResponseWriter;
import com.luxoft.sdemenkov.database.server.util.ResponseMapper;
import com.luxoft.sdemenkov.database.server.util.impl.ResponseMapperJson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private int port = 3000;
    private String rootResourceFolder;
    private RequestHandler requestHandler;
    private ResponseWriter responseWriter;

    public void start() {
        requestHandler = new RequestHandler();
        configure();

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            requestHandler.setSocketReader(socketReader);
            responseWriter.setWriter(socketWriter);
            while (true) {
                try {
                    requestHandler.handle();
                } catch (RuntimeException e) {
                    ResponseBuilder builder = ResponseBuilder.start();
                    builder.addMessage(e.getMessage());
                    responseWriter.write(builder.build());
                }
            }
        } catch (SocketException| SocketRuntimeException e) {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void configure() {
        DbObjectSearcher searcher = new DbObjectSearcher();
        DbFactory factory = new DbFactoryXml();
        RequestParser parser = new RequestParserJson();
        RequestExecutor executor = new RequestExecutor();
        responseWriter = new ResponseWriter();
        ResponseMapper responseMapper = new ResponseMapperJson();

        responseWriter.setResponseMapper(responseMapper);
        searcher.setDbFactory(factory);
        searcher.setRootResourceFolder(rootResourceFolder);

        executor.setDbObjectSearcher(searcher);

        requestHandler.setRequestParser(parser);
        requestHandler.setRequestExecutor(executor);
        requestHandler.setResponseWriter(responseWriter);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRootResourceFolder(String rootResourceFolder) {
        this.rootResourceFolder = rootResourceFolder;
    }
}
