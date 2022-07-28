package com.frequentis.chica.socket.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket {

    private final String name;
    private final String hostname;
    private final int port;

    private Socket socket;

    public ClientSocket(String name, String hostname, int port) {
        this.name = name;
        this.hostname = hostname;
        this.port = port;
    }

    public void openConnection() {
        try {
            this.socket = new Socket(hostname, port);
        } catch (Exception e) {
            System.out.println("Cannot open connection");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            try {
                DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
                outputStream.writeUTF(message + " (local port " + socket.getLocalPort() + ")");
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("socket issues");
        }
    }

    public String getName() {
        return name;
    }
}
