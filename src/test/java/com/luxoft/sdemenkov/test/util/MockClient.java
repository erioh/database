package com.luxoft.sdemenkov.test.util;

import java.io.*;
import java.net.Socket;
import java.util.StringJoiner;

public class MockClient {
    public static void main(String[] args) {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             Socket socket = new Socket("localhost", 3000);
             BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String value;
            while (true) {
                StringBuilder builder = new StringBuilder();
                while (!(value = consoleReader.readLine()).equalsIgnoreCase("send")) {
                    builder.append(value);
                    builder.append("\r\n");
                }
                socketWriter.write(builder.toString());
                socketWriter.flush();
                String valueIn;
                while (!"".equals(valueIn = socketReader.readLine())) {
                    System.out.println(valueIn);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
