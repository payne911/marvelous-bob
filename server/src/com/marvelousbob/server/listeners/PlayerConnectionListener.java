package com.marvelousbob.server.listeners;

import static com.marvelousbob.common.network.constants.GameConstant.sizeX;
import static com.marvelousbob.common.network.constants.GameConstant.sizeY;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.marvelousbob.common.model.entities.Player;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.PlayerConnectionDto;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.model.ServerState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerConnectionListener extends AbstractListener<PlayerConnectionDto> {

    private final Server server;
    private final ServerState serverState;

    public PlayerConnectionListener(Server server, ServerState serverState) {
        super(PlayerConnectionDto.class);
        this.server = server;
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection connection, PlayerConnectionDto playerConnection) {
        if (serverState.isEmptyRoom()) {
            serverState.initializeOnFirstPlayerConnected();
        }
        UUID uuid = UUID.getNext();
        Player player = playerConnection.playerType
                .getPlayerInstance(uuid, serverState.getFreeColor(), randomPos());
        serverState.addPlayer(player);
        server.sendToTCP(connection.getID(), serverState.getGameInitDto(uuid));
        server.sendToAllExceptTCP(connection.getID(), player);
    }


    private Vector2 randomPos() {
        float x = MathUtils.random(0, sizeX);
        float y = MathUtils.random(0, sizeY);
        return new Vector2(x, y);
    }
}
