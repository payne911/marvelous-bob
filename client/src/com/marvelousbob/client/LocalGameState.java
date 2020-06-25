package com.marvelousbob.client;

import com.marvelousbob.client.entities.Drawable;
import com.marvelousbob.client.entities.Enemy;
import com.marvelousbob.client.entities.Player;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalGameState implements Drawable {

    private List<Player> players;
    private Set<Enemy> enemies;

    public LocalGameState() {
        this.players = new ArrayList<>();
        this.enemies = new HashSet<>();
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        players.forEach(p -> p.drawMe(shapeDrawer));
        enemies.forEach(e -> e.drawMe(shapeDrawer));
    }
}
