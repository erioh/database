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
            StringJoiner joiner = new StringJoiner("\r\n");
            while (!(value = consoleReader.readLine()).equalsIgnoreCase("send")) {
                joiner.add(value);
            }
            socketWriter.write(joiner.toString());
            socketWriter.flush();
            String valueIn;
            while ((valueIn = socketReader.readLine()) != null) {
                System.out.println(valueIn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
