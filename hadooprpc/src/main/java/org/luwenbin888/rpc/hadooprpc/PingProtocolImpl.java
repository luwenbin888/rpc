package org.luwenbin888.rpc.hadooprpc;

public class PingProtocolImpl implements PingProtocol{
    public String ping() {
        System.out.println("Server ping receive");
        return "pong";
    }
}
