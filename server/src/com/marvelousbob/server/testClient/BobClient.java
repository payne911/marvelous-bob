package com.marvelousbob.server.testClient;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.common.register.Msg;
import com.marvelousbob.common.register.Register;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetAddress;

import static com.marvelousbob.server.ServerInit.PORT;


public class BobClient {

    static int id = 0;
    @Getter
    private Client client;

    @Getter
    private IncrementalAverage latency;

    int clientId;
    InetAddress addr;

    public BobClient() {
        this(null);
    }

    public BobClient(InetAddress addr) {
        this.client = new Client();
        Register.registerClasses(client);
        this.clientId = id++;
        this.addr = addr;
        latency = new IncrementalAverage();
    }

    @SneakyThrows
    public void connect() {
        client.addListener(new Listener.ThreadedListener(new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof Msg) {
                    Msg m = (Msg) o;
                    latency.addToRunningAverage(m.getTimestamp());
                }
            }
        }));
        client.start();
        client.connect(5000, addr == null ? InetAddress.getLocalHost() : addr, PORT);
    }
}
