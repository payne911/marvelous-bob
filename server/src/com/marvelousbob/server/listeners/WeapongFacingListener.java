package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.WeaponFacingDto;
import com.marvelousbob.server.model.ServerState;

public class WeapongFacingListener extends AbstractListener<WeaponFacingDto> {

    private final ServerState serverState;

    public WeapongFacingListener(ServerState serverState) {
        super(WeaponFacingDto.class);
        this.serverState = serverState;
    }

    @Override
    public void accept(Connection conncetion, WeaponFacingDto elem) {
        serverState.updatePlayerFacingAngle(elem.playerUuid, elem.angle);
    }
}
