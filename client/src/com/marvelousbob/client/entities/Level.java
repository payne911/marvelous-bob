package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Intersector;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.*;

@Data
public class Level implements Drawable {

    private final Intersector intersector;

    private final List<Base> base;
    private final List<EnemySpawn> enemySpawns;
    private final List<Wall> walls;
    private List<Player> players = new ArrayList<>();
    private Set<Ennemy> ennemies = Collections.newSetFromMap(new IdentityHashMap<>());

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        base.forEach(b -> b.drawMe(shapeDrawer));
        walls.forEach(w -> w.drawMe(shapeDrawer));
        players.forEach(p -> p.drawMe(shapeDrawer));
        ennemies.forEach(e -> e.drawMe(shapeDrawer));
        enemySpawns.forEach(es -> es.drawMe(shapeDrawer));
    }

}
