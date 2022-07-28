package com.frequentis.chica.socket.client;


import com.frequentis.chica.socket.server.ServerSocket;
import org.junit.jupiter.api.Test;

class SocketClientTest {

    @Test
    void testClient() throws InterruptedException {
        new ServerSocket().startServer(7777);
        ClientSocket client1 = new ClientSocket("Client1", "localhost", 7777);
        client1.openConnection();
        Thread.sleep(2000);
//        TestSocketClient client2 = new TestSocketClient("Client2", "localhost", 7777);
//        client2.openConnection();
        client1.sendMessage("Hello from " + client1.getName());
        Thread.sleep(2000);
//        client2.sendMessage("Hello from " + client2.getName());
//        TestSocketClient client3 = new TestSocketClient("Client3", "localhost", 7777);
//        client3.openConnection();
//        client3.sendMessage("Hello from " + client3.getName());
        client1.sendMessage("Bye from " + client1.getName());
        Thread.sleep(2000);
//        client2.sendMessage("Bye from " + client2.getName());
//        Thread.sleep(1000);
//        client3.sendMessage("Bye from " + client3.getName());
    }
}