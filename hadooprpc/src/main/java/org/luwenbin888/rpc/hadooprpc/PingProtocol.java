package org.luwenbin888.rpc.hadooprpc;

import org.apache.hadoop.ipc.ProtocolInfo;

@ProtocolInfo(protocolName = "ping", protocolVersion = 1)
public interface PingProtocol {
    String ping();
}
