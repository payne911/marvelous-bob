package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.model.entities.dynamic.projectiles.RangedPlayerBullet;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.RangedPlayerAttackDto;
import com.marvelousbob.common.state.LocalGameState;

public class RangedPlayerAttackListener extends AbstractListener<RangedPlayerAttackDto> {

    private LocalGameState localGameState;

    public RangedPlayerAttackListener(LocalGameState localGameState) {
        super(RangedPlayerAttackDto.class);
        this.localGameState = localGameState;
    }

    @Override
    public void accept(Connection conncetion, RangedPlayerAttackDto elem) {
        // TODO: 2020-07-03 deal with timestamp?    --- OLA
        localGameState.getRangedPlayerById(elem.uuid)
                .ifPresent(rp -> {
                    rp.addBullet(new RangedPlayerBullet(
                            elem.initPos,
                            elem.clickedPos,
                            rp.getColor(),
                            elem.bulletSpeed,
                            elem.bulletRadius
                    ));
                });
    }
}
