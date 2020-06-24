package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Intersector;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;
import java.util.Set;

@Data
public class Level implements Drawable {

    private Intersector intersector = new Intersector();
    List<Wall> walls = new ArrayList<>();
    List<Player> players = new ArrayList<>();
    Set<Ennemy> ennemies = new HashSet<>();

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        walls.forEach(w -> w.drawMe(shapeDrawer));
        players.forEach(p -> p.drawMe(shapeDrawer));
    }
}
