package com.marvelousbob.common.model.entities.dynamic.allies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
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
        shapeDrawer.rectangle(getCurrCenterX() - size / 2, getCurrCenterY() - size / 2, size, size);
    }
}
