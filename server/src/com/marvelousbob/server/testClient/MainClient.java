package com.marvelousbob.server.testClient;

import com.marvelousbob.common.register.Msg;

import java.io.IOException;
import java.net.InetAddress;

public class MainClient {
    public static final String EC2_HOST = "52.60.181.140";

    public static void main(String[] args) throws IOException {
//        BobClient client = new BobClient(InetAddress.getLocalHost());
        BobClient client = new BobClient(InetAddress.getByName(EC2_HOST));
        client.connect();
        client.getClient().sendTCP(new Msg("current timestamp", System.currentTimeMillis()));
    }
}

