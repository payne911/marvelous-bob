package com.marvelousbob.client;

import com.marvelousbob.client.entities.Drawable;
import com.marvelousbob.client.entities.Enemy;
import com.marvelousbob.client.entities.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class LocalGameState implements Drawable {

    private List<Player> players;
    private Set<Enemy> enemies;

    public LocalGameState() {
        this.players = new ArrayList<>();
        this.enemies = Collections.newSetFromMap(new IdentityHashMap<>());
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        players.forEach(p -> p.drawMe(shapeDrawer));
        enemies.forEach(e -> e.drawMe(shapeDrawer));
    }
}
