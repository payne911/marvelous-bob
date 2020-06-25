package com.marvelousbob.client.entities;

import com.marvelousbob.common.network.register.dto.PlayerDto;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.HashSet;
import java.util.Set;

public class RangedPlayer extends Player {

    private Set<TrianglePlayerBullet> bullets;


    public RangedPlayer(PlayerDto playerDto) {
        super(playerDto);
        this.bullets = new HashSet<>();
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        super.drawMe(shapeDrawer);
    }


}
