package com.marvelousbob.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.client.LocalGameState;
import com.marvelousbob.common.network.constants.GameConstant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class GameWorld implements Drawable {

    private final ShapeDrawer shapeDrawer;
    private LocalGameState localGameState;
    private Level level;

    public void generateLevel() {
        Base base = new Base(
                new Rectangle(GameConstant.sizeX / 2f, GameConstant.sizeY / 2f, 50, 50));

        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(new Rectangle(30, 50, 2, 60)));
        walls.add(new Wall(new Rectangle(70, 150, 2, 100)));
        walls.add(new Wall(new Rectangle(170, 360, 30, 2)));

        List<EnemySpawnPoint> enemySpawnPoints = new ArrayList<>();
        enemySpawnPoints.add(EnemySpawnPoint.starShaped(new Vector2(70, 70), 15, Color.PINK));
        enemySpawnPoints.add(EnemySpawnPoint.starShaped(new Vector2(270, 270), 15, Color.PINK));

        level = new Level(List.of(base), enemySpawnPoints, walls);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        level.drawMe(shapeDrawer);
        localGameState.drawMe(shapeDrawer);
    }
}
