package com.marvelousbob.common.model.entities.dynamic.allies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.projectiles.RangedPlayerBullet;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import java.util.HashSet;
import java.util.Set;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
public class RangedPlayer extends Player {

    private Set<RangedPlayerBullet> bullets;


    public RangedPlayer(PlayerDto playerDto) {
        super(playerDto);
        this.bullets = new HashSet<>();
    }

    public RangedPlayer(UUID uuid, Color color, Vector2 initCenterPos) {
        super(100, 100, 0, color, 40, 20, initCenterPos, uuid);
        this.bullets = new HashSet<>();
    }

    @Override
    public void updateFromDto(PlayerDto input) {
        super.updateFromDto(input);
//        this.bullets = input.getBullets().stream().map(b -> new TrianglePlayerBullet()) todo
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(this.color);
        shapeDrawer.circle(getCurrCenterX(), getCurrCenterY(), getSize() / 2);
        bullets.forEach(b -> b.drawMe(shapeDrawer));
    }
}
