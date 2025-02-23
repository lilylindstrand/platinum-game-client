package dev.hepno.platinum_game_client.session;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dev.hepno.platinum_game_client.PlatinumGameClientApplication;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

public class Authenticator {

    private String sessionId = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private PlatinumGameClientApplication main;

    public Authenticator(PlatinumGameClientApplication main) {
        this.main = main;
    }

    public void openLoginSite() throws IOException, InterruptedException {
        String loginUrl = "http://localhost:8080/login";

        Desktop desktop = Desktop.getDesktop();
        desktop.browse(URI.create(loginUrl));
    }

    public void startListener() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(5959), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String path = exchange.getRequestURI().getPath();
                sessionId = path.substring(1);
                latch.countDown();
            }
        });

        server.start();
    }

    public void loginProcess() throws IOException, InterruptedException {
        startListener();
        openLoginSite();
        latch.await();
        main.setSession(new Session(sessionId));
    }

}
