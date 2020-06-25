package com.marvelousbob.client.entities;

import com.marvelousbob.common.network.register.dto.PlayerDto;
import java.util.HashSet;
import java.util.Set;
import space.earlygrey.shapedrawer.ShapeDrawer;

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

    @Override
    public void updateFromDto(PlayerDto input) {
        super.updateFromDto(input);
//        this.bullets = input.getBullets().stream().map(b -> new TrianglePlayerBullet()) todo
    }
}
