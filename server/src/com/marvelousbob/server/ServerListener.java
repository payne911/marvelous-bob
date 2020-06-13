package com.marvelousbob.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.register.dto.Msg;
import com.marvelousbob.common.network.register.dto.Ping;

public class ServerListener implements Listener {
    private final Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("[SERVER SIDE] : Connected with " + connection.getRemoteAddressTCP().toString());
        server.sendToTCP(connection.getID(), new Msg("connection success!"));
    }

    @Override
    public void received(Connection connection, Object receivedObject) {
        if (receivedObject instanceof Msg msg) onMsg(connection, msg);
        if (receivedObject instanceof Ping ping) onPing(connection, ping);
    }

    private void onPing(Connection connection, Ping ping) {
        server.sendToTCP(connection.getID(), ping);
    }

    private void onMsg(Connection connection, Msg msg) {
        server.sendToTCP(connection.getID(), msg);
    }
}
