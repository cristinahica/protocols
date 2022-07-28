package com.frequentis.chica.socket.server;

import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerSocket {

    private final static Executor EXECUTOR = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        new ServerSocket().startServer(Integer.parseInt(args[0]));
    }

    public void startServer(int port) {
        EXECUTOR.execute(() -> this.doStartServer(port));
    }

    public void doStartServer(int port) {
        System.out.println("Starting Socket Server on port " + port);
        try (java.net.ServerSocket serverSocket = new java.net.ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                EXECUTOR.execute(new ClientHandler(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
