package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Intersector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Level implements Drawable {

    private final Intersector intersector;
    private final Base base;
    private final List<Wall> walls;
    private List<Player> players = new ArrayList<>();
    private Set<Ennemy> ennemies = new HashSet<>();

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        walls.forEach(w -> w.drawMe(shapeDrawer));
        players.forEach(p -> p.drawMe(shapeDrawer));
    }
}
