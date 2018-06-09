package org.luwenbin888.rpc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    AtomicBoolean exit = new AtomicBoolean(false);

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Echo server started, listening at 10005");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                if ("exit".equals(inputLine)) {
                    out.println("Server is exiting, bye!");
                    exit.set(true);
                }
                out.println(inputLine);
            }

            in.close();
            out.close();
            clientSocket.close();

            if (exit.get()) {
                System.out.println("Exiting Echo Server...");
                break;
            }
        }

        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        EchoMultiServer echoMultiServer = new EchoMultiServer();
        echoMultiServer.start(10005);
    }
}
