package com.marvelousbob.server;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.register.dto.*;

import static com.marvelousbob.common.network.constants.GameConstant.sizeX;
import static com.marvelousbob.common.network.constants.GameConstant.sizeY;

public class ServerListener implements Listener {
    private final Server server;
    private final GameStateDto gameStateDto;

    public ServerListener(Server server, GameStateDto gameStateDto) {
        this.server = server;
        this.gameStateDto = gameStateDto;
    }


    @Override
    public void connected(Connection connection) {
        System.out.println("[SERVER SIDE] : Connected with " + connection.getRemoteAddressTCP().toString());
    }


    @Override
    public void received(Connection connection, Object receivedObject) {
        if (receivedObject instanceof Msg msg) onMsg(connection, msg);
        if (receivedObject instanceof Ping ping) onPing(connection, ping);
        if (receivedObject instanceof PlayerConnection) onNewPlayerConnection(connection);
    }


    private synchronized void onNewPlayerConnection(Connection connection) {
        UUID uuid = UUID.randomUUID();
        PlayerDto playerDto = new PlayerDto(uuid);
        float x = MathUtils.random(0, sizeX);
        float y = MathUtils.random(0, sizeY);
        playerDto.setCurrX(x);
        playerDto.setCurrY(y);
        playerDto.setDestX(x);
        playerDto.setDestY(y);
        gameStateDto.getPlayerDtos().add(playerDto);
        server.sendToTCP(connection.getID(), new GameIntialization(gameStateDto, uuid));
    }


    private void onPing(Connection connection, Ping ping) {
        server.sendToTCP(connection.getID(), ping);
    }


    private void onMsg(Connection connection, Msg msg) {
        server.sendToTCP(connection.getID(), msg);
    }
}
