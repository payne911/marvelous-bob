package com.marvelousbob.server.listeners;

import static com.marvelousbob.common.network.constants.GameConstant.sizeX;
import static com.marvelousbob.common.network.constants.GameConstant.sizeY;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.PlayerConnection;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.model.ServerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerConnectionListener extends AbstractListener<PlayerConnection> {

    private final Server server;
    private final ServerState serverState;

    public PlayerConnectionListener(Server server, ServerState serverState) {
        super(PlayerConnection.class);
        this.server = server;
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection connection, PlayerConnection playerConnection) {
        UUID uuid = UUID.getNext();
        PlayerDto playerDto = new PlayerDto(uuid);
        assignRandomPosition(playerDto);
        assignRandomColorId(playerDto);
        serverState.addPlayer(playerDto);
        server.sendToTCP(connection.getID(), serverState.getInitializationDto(uuid));
    }

    private void assignRandomColorId(PlayerDto playerDto) {
        try {
            playerDto.setColorIndex(serverState.getFreeId());
        } catch (MarvelousBobException ex) {
            log.error("Could not find a free Color ID: room must be full.");
            ex.printStackTrace();
        }
    }

    private void assignRandomPosition(PlayerDto playerDto) {
        float x = MathUtils.random(0, sizeX);
        float y = MathUtils.random(0, sizeY);
        playerDto.setCurrX(x);
        playerDto.setCurrY(y);
        playerDto.setDestX(x);
        playerDto.setDestY(y);
    }
}
