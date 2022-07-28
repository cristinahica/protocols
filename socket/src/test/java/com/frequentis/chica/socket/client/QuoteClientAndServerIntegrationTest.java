package com.frequentis.chica.socket.client;


import org.junit.jupiter.api.Test;

import com.frequentis.chica.socket.server.QuoteServer;

class QuoteClientAndServerIntegrationTest {

    @Test
    void testClient() {
        new QuoteClient("djxmmx.net", 17);
    }

    @Test
    void testServer() {
        new QuoteServer().startServer(7777);
        new QuoteClientLocal("localhost", 7777);
    }
}