package com.marvelousbob.server.worlds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.EnemySpawnPoint;
import com.marvelousbob.common.model.entities.Level;
import com.marvelousbob.common.model.entities.PlayersBase;
import com.marvelousbob.common.model.entities.Wall;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class StaticSimpleLevelGenerator implements LevelGenerator {

    @Override
    public Level getLevel() {
        final ConcurrentHashMap<UUID, PlayersBase> bases = new ConcurrentHashMap<>();
        UUID baseUuid = UUID.getNext();
        var base = new PlayersBase(baseUuid, 50, 50, 100, 100);
        bases.put(baseUuid, base);

        final ConcurrentHashMap<UUID, EnemySpawnPoint> enemySpawnPoints = new ConcurrentHashMap<>();
        UUID spawnUuid1 = UUID.getNext();
        UUID spawnUuid2 = UUID.getNext();
        enemySpawnPoints.put(spawnUuid1,
                EnemySpawnPoint.starShaped(spawnUuid1, new Vector2(60, 60), 70, Color.BLUE));
        enemySpawnPoints.put(spawnUuid2,
                EnemySpawnPoint.starShaped(spawnUuid2, new Vector2(60, 60), 70, Color.BLUE));

        final ArrayList<Wall> walls = new ArrayList<>();
        walls.add(new Wall(30, 50, 2, 60));
        walls.add(new Wall(70, 150, 2, 100));
        walls.add(new Wall(170, 360, 30, 2));

        return new Level(bases, enemySpawnPoints, walls);
    }
}
