package com.marvelousbob.client.entities;

import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.PlayerDto;
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

    @Override
    public void updateFromDto(PlayerDto input) {
        super.updateFromDto(input);
//        this.bullets = input.getBullets().stream().map(b -> new TrianglePlayerBullet()) todo
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(GameConstant.playerColors.get(colorIndex));
        shapeDrawer.polygon(VERTICES);
        bullets.forEach(b -> b.drawMe(shapeDrawer));
    }
}
