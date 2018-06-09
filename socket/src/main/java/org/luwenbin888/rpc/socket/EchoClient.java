package org.luwenbin888.rpc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 10002);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("hello");
        String resp1 = in.readLine();
        System.out.println(resp1);

        out.println("world");
        String resp2 = in.readLine();
        System.out.println(resp2);

        out.println("!");
        String resp3 = in.readLine();
        System.out.println(resp3);

        out.println(".");
        String resp4 = in.readLine();
        System.out.println(resp4);

        out.close();
        in.close();
        socket.close();
    }
}
