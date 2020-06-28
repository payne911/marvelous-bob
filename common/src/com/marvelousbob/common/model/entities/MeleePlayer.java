package com.marvelousbob.common.model.entities;

import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class MeleePlayer extends Player {

    public MeleePlayer(PlayerDto playerDto) {
        super(playerDto);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(GameConstant.playerColors.get(colorIndex));
        shapeDrawer.rectangle(currentPos.x - size / 2, currentPos.y - size / 2, size, size);
    }
}
