package com.marvelousbob.client;

import com.marvelousbob.client.entities.Enemy;
import com.marvelousbob.client.entities.Player;

import java.util.*;

public class LocalGameState {

    private List<Player> players;
    private Set<Enemy> enemies;

    public LocalGameState() {
        this.players = new ArrayList<>();
        this.enemies = Collections.newSetFromMap(new IdentityHashMap<>());
    }
}
