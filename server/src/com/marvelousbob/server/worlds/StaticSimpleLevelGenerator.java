package com.marvelousbob.server.worlds;

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
//        ArrayList<PlayersBaseDto> bases = new ArrayList<>();
//        bases.add(new PlayersBaseDto(UUID.getNext(), GameConstant.sizeX / 2f, GameConstant.sizeY / 2f, 50, 50, 100, 100));
//
//        ArrayList<WallDto> walls = new ArrayList<>();
//        walls.add(new WallDto(30, 50, 2, 60));
//        walls.add(new WallDto(70, 150, 2, 100));
//        walls.add(new WallDto(170, 360, 30, 2));
//
//        ArrayList<SpawnPointDto> enemySpawnPoints = new ArrayList<>();
//        enemySpawnPoints.add(new SpawnPointDto(100, UUID.getNext()));
//        enemySpawnPoints.add(new SpawnPointDto(100, UUID.getNext()));

        final ConcurrentHashMap<UUID, PlayersBase> bases = new ConcurrentHashMap<>();
        final ConcurrentHashMap<UUID, EnemySpawnPoint> enemySpawnPoints = new ConcurrentHashMap<>();
        final ArrayList<Wall> walls = new ArrayList<>();
        // TODO: 2020-06-28 make a level     --- OLA
        return new Level(bases, enemySpawnPoints, walls);
    }
}
