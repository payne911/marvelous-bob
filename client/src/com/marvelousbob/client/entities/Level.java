package com.marvelousbob.client.entities;

import java.util.ArrayList;
import java.util.List;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Level implements Drawable {

    List<Wall> walls = new ArrayList<>();
    List<Player> players = new ArrayList<>();

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        walls.forEach(w -> w.drawMe(shapeDrawer));
        players.forEach(p -> p.drawMe(shapeDrawer));
    }
}
