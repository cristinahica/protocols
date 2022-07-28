/*
 * COPYRIGHT: FREQUENTIS AG. All rights reserved.
 *            Registered with Commercial Court Vienna,
 *            reg.no. FN 72.115b.
 */
package com.frequentis.chica.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QuoteServer {
    private final static Executor EXECUTOR = Executors.newFixedThreadPool(3);
    public static final int PORT_NUMBER = 7777;
    public static final String QUOTES_TXT = "quotes.txt";

    private DatagramSocket socket;
    private final List<String> listQuotes = new ArrayList<>();
    private Random random;

    public static void main(String[] args) {
        new QuoteServer().startServer(PORT_NUMBER);
    }

    public void startServer(final int port) {
        EXECUTOR.execute(() -> this.doStartServer(port));
    }

    public void doStartServer(final int port) {
        try {
            socket = new DatagramSocket(port);
            random = new Random();
            loadQuotesFromFile();
            service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);

            String quote = getRandomQuote();
            byte[] buffer = quote.getBytes();

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
        }
    }

    private void loadQuotesFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(QUOTES_TXT)));
        String aQuote;

        while ((aQuote = reader.readLine()) != null) {
            listQuotes.add(aQuote);
        }

        reader.close();
    }

    private String getRandomQuote() {
        int randomIndex = random.nextInt(listQuotes.size());
        return listQuotes.get(randomIndex);
    }
}
