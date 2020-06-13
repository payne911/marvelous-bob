package com.marvelousbob.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.events.PlayerConnection;
import com.marvelousbob.common.network.register.dto.*;

import java.util.Random;

import static com.marvelousbob.common.network.constants.GameConstant.sizeX;
import static com.marvelousbob.common.network.constants.GameConstant.sizeY;

public class ServerListener implements Listener {
    private final Server server;
    private GameState gameState;
    private Random random;

    public ServerListener(Server server, GameState gameState) {
        this.server = server;
        this.gameState = gameState;
        this.random = new Random();
    }


    @Override
    public void connected(Connection connection) {
        System.out.println("[SERVER SIDE] : Connected with " + connection.getRemoteAddressTCP().toString());
    }


    @Override
    public void received(Connection connection, Object receivedObject) {
        if (receivedObject instanceof Msg msg) onMsg(connection, msg);
        if (receivedObject instanceof Ping ping) onPing(connection, ping);
        if (receivedObject instanceof PlayerConnection playerConnection)
            onNewPlayerConnection(playerConnection, connection);
    }


    private synchronized void onNewPlayerConnection(PlayerConnection playerConnection, Connection connection) {
        UUID uuid = UUID.randomUUID();
        PlayerDto playerDto = new PlayerDto(uuid);
        playerDto.setCurrX(random.nextFloat() * sizeX);
        playerDto.setCurrY(random.nextFloat() * sizeY);
        gameState.getPlayerDtos().add(playerDto);
        server.sendToTCP(connection.getID(), new GameIntialization(gameState, uuid));
        playerConnection.run();
    }


    private void onPing(Connection connection, Ping ping) {
        server.sendToTCP(connection.getID(), ping);
    }


    private void onMsg(Connection connection, Msg msg) {
        server.sendToTCP(connection.getID(), msg);
    }
}
