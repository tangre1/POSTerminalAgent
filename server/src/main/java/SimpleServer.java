import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.InputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleServer {

    static String lastData = "{}";

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);

        server.createContext("/checkin", new CheckinHandler());

        server.createContext("/data", new DataHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server running on http://localhost:8080");
    }

    static class CheckinHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            if ("POST".equals(exchange.getRequestMethod())) {

                InputStream is = exchange.getRequestBody();
                byte[] data = is.readAllBytes();
                String body = new String(data);

                lastData = body;

                System.out.println("\nReceived POST:");
                System.out.println(body);

                String response = "OK";

                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());

            } else {
                exchange.sendResponseHeaders(405, -1);
            }

            exchange.close();
        }
    }

    static class DataHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String response = lastData;

            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());

            exchange.close();
        }
    }
}