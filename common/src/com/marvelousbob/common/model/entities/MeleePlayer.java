package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class MeleePlayer extends Player {

    public MeleePlayer(PlayerDto playerDto) {
        super(playerDto);
    }

    public MeleePlayer(UUID uuid, Color color, Vector2 initPos) {
        super(100, 100, 0, color, 40, 20, initPos, uuid);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(this.color);
        shapeDrawer.rectangle(currentPos.x - size / 2, currentPos.y - size / 2, size, size);
    }
}
