package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import java.util.HashSet;
import java.util.Set;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class RangedPlayer extends Player {

    private static final float[] VERTICES = new float[]{
            0, 0,
            16, 32,
            32, 0,
            16, 10
    };

    private Set<TrianglePlayerBullet> bullets;


    public RangedPlayer(PlayerDto playerDto) {
        super(playerDto);
        this.bullets = new HashSet<>();
    }

    public RangedPlayer(UUID uuid, Color color, Vector2 initPos) {
        super();
    }

    @Override
    public void updateFromDto(PlayerDto input) {
        super.updateFromDto(input);
//        this.bullets = input.getBullets().stream().map(b -> new TrianglePlayerBullet()) todo
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(this.color);
        shapeDrawer.polygon(VERTICES);
        bullets.forEach(b -> b.drawMe(shapeDrawer));
    }
}
