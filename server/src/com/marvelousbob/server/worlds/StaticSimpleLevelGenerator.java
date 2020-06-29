package com.marvelousbob.server.worlds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.level.EnemySpawnPoint;
import com.marvelousbob.common.model.entities.level.Level;
import com.marvelousbob.common.model.entities.level.PlayersBase;
import com.marvelousbob.common.model.entities.level.Wall;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.factories.WallFactory;
import com.marvelousbob.server.factories.WallFactory.ORIENTATION;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class StaticSimpleLevelGenerator implements LevelGenerator {

    private final WallFactory wallFactory;

    public StaticSimpleLevelGenerator() {
        this.wallFactory = new WallFactory();
    }

    @Override
    public Level getLevel() {
        final ConcurrentHashMap<UUID, PlayersBase> bases = new ConcurrentHashMap<>();
        UUID baseUuid = UUID.getNext();
        var base = PlayersBase.hexagonalPlayerBase(baseUuid,
                new Vector2(GameConstant.sizeX / 2f, GameConstant.sizeY / 2f),
                35, Color.FIREBRICK);
        bases.put(baseUuid, base);

        final ConcurrentHashMap<UUID, EnemySpawnPoint> enemySpawnPoints = new ConcurrentHashMap<>();
        UUID spawnUuid1 = UUID.getNext();
        UUID spawnUuid2 = UUID.getNext();
        enemySpawnPoints.put(spawnUuid1,
                EnemySpawnPoint.starShaped(spawnUuid1, new Vector2(60, 60), 70, Color.BLUE));
        enemySpawnPoints.put(spawnUuid2,
                EnemySpawnPoint.starShaped(spawnUuid2, new Vector2(460, 460), 70, Color.BLUE));

        final ArrayList<Wall> walls = new ArrayList<>();
        walls.add(buildWall(ORIENTATION.VERTICAL, new Vector2(80, 150), 100));
        walls.add(buildWall(ORIENTATION.HORIZONTAL, new Vector2(80, 150), 150));
        walls.add(buildWall(ORIENTATION.VERTICAL, new Vector2(80 + 150, 150), 150));

        return new Level(bases, enemySpawnPoints, walls);
    }

    private Wall buildWall(ORIENTATION orientation, Vector2 pos, float length) {
        return wallFactory.buildWall(orientation, pos, length);
    }
}
