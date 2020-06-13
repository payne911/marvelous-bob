package com.marvelousbob.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.register.dto.GameState;
import com.marvelousbob.common.network.register.dto.Msg;
import com.marvelousbob.common.network.register.dto.Ping;
import com.marvelousbob.common.network.register.dto.Player;

import java.util.UUID;

public class ServerListener extends Listener {
    private final Server server;
    private GameState gameState;

    public ServerListener(Server server, GameState gameState) {
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("[SERVER SIDE] : Connected with " + connection.getRemoteAddressTCP().toString());
        UUID uuid = UUID.randomUUID();
        Player player = new Player(uuid);
        player.setSelf(true);
        gameState.getPlayers().add(player);
        server.sendToTCP(connection.getID(), player);
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
