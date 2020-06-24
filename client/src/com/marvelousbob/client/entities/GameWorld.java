package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.marvelousbob.common.network.constants.GameConstant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class GameWorld {

    private final Intersector intersector = new Intersector();
    private Level level;

    public void generateLevel(List<Player> players) {
        Base base = new Base(
                new Rectangle(GameConstant.sizeX / 2f, GameConstant.sizeY / 2f, 50, 50));

        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(new Rectangle(30, 50, 2, 60)));
        walls.add(new Wall(new Rectangle(70, 150, 2, 100)));
        walls.add(new Wall(new Rectangle(170, 360, 30, 2)));

        Level newLevel = new Level(intersector, base, walls);
        newLevel.setPlayers(players);
    }
}
