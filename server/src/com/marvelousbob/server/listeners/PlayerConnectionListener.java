package com.marvelousbob.server.listeners;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.*;

import static com.marvelousbob.common.network.constants.GameConstant.sizeX;
import static com.marvelousbob.common.network.constants.GameConstant.sizeY;

public class PlayerConnectionListener extends AbstractListener<PlayerConnection> {

    private final Server server;
    private final GameStateDto gameStateDto;

    public PlayerConnectionListener(Server server, GameStateDto gameStateDto) {
        super(PlayerConnection.class);
        this.server = server;
        this.gameStateDto = gameStateDto;
    }

    @Override
    public void accept(Connection connection, PlayerConnection playerConnection) {

        UUID uuid = UUID.randomUUID();
        System.out.println("SENDING UUID: " + uuid);
        PlayerDto playerDto = new PlayerDto(uuid);
        float x = MathUtils.random(0, sizeX);
        float y = MathUtils.random(0, sizeY);
        playerDto.setCurrX(x);
        playerDto.setCurrY(y);
        playerDto.setDestX(x);
        playerDto.setDestY(y);
        gameStateDto.addPlayer(playerDto);
        server.sendToTCP(connection.getID(), new GameInitialization(gameStateDto, uuid));
    }
}
