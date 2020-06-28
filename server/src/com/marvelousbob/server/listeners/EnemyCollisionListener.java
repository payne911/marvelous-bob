package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.EnemyCollisionDto;
import com.marvelousbob.server.model.ServerState;

public class EnemyCollisionListener extends AbstractListener<EnemyCollisionDto> {

    private final ServerState serverState;

    public EnemyCollisionListener(ServerState serverState) {
        super(EnemyCollisionDto.class);
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection conncetion, EnemyCollisionDto elem) {
        serverState.getEnemyCollisions().add(elem);
    }
}
