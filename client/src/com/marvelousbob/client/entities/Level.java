package com.marvelousbob.client.entities;

import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Level implements Drawable {

    List<Wall> walls = new ArrayList<>();
    List<Player> players = new ArrayList<>();
    Set<Ennemy> ennemies = new HashSet<>();

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        walls.forEach(w -> w.drawMe(shapeDrawer));
        players.forEach(p -> p.drawMe(shapeDrawer));
    }
}
