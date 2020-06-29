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
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidgrid.mapping.GrowingTreeMazeGenerator;
import squidpony.squidmath.GreasedRegion;
import squidpony.squidmath.StatefulRNG;

public class ProceduralLevelGenerator implements LevelGenerator {

    private final WallFactory wallFactory;

    public ProceduralLevelGenerator() {
        this.wallFactory = new WallFactory();
    }

    @Override
    public Level getLevel() {
        final ConcurrentHashMap<UUID, PlayersBase> bases = new ConcurrentHashMap<>();
        UUID baseUuid = UUID.getNext();
        var base = PlayersBase.hexagonalPlayerBase(baseUuid,
                new Vector2(GameConstant.SIZE_X / 2f, GameConstant.SIZE_Y / 2f),
                35, Color.FIREBRICK);
        bases.put(baseUuid, base);

        final ConcurrentHashMap<UUID, EnemySpawnPoint> enemySpawnPoints = new ConcurrentHashMap<>();
        UUID spawnUuid1 = UUID.getNext();
        UUID spawnUuid2 = UUID.getNext();
        enemySpawnPoints.put(spawnUuid1,
                EnemySpawnPoint.starShaped(spawnUuid1, new Vector2(60, 60), 30, Color.BLUE));
        enemySpawnPoints.put(spawnUuid2,
                EnemySpawnPoint.starShaped(spawnUuid2, new Vector2(460, 460), 30, Color.BLUE));

        final ArrayList<Wall> walls = new ArrayList<>();
        walls.add(buildWall(ORIENTATION.VERTICAL, new Vector2(80, 150), 100));
        walls.add(buildWall(ORIENTATION.HORIZONTAL, new Vector2(80, 150), 150));
        walls.add(buildWall(ORIENTATION.VERTICAL, new Vector2(80 + 150, 150), 200));
        walls.add(buildWall(ORIENTATION.HORIZONTAL, new Vector2(80 + 150, 350), 350));
        walls.add(buildWall(ORIENTATION.HORIZONTAL, new Vector2(80 + 150, 250), 350));

        return new Level(bases, enemySpawnPoints, walls);
    }

    private Wall buildWall(ORIENTATION orientation, Vector2 pos, float length) {
        return wallFactory.buildWall(orientation, pos, length);
    }

    public static void test() {
        StatefulRNG rng = new StatefulRNG(123L); // change seed to change level
        GrowingTreeMazeGenerator mazeGenerator =
                new GrowingTreeMazeGenerator(GameConstant.BLOCKS_X, GameConstant.BLOCKS_Y, rng);
        char[][] map = mazeGenerator.generate();
        GreasedRegion walls = new GreasedRegion(map, '#');
        GreasedRegion temp = walls.copy();
        walls.deteriorate(rng, 0.85);
        walls.or(temp.refill(mazeGenerator.generate(), '#').deteriorate(rng, 0.85));
        walls.andNot(walls.copy().neighborDownRight()).removeIsolated().not().removeEdges()
                .intoChars(map, '.', '#');
        char[][] grid = DungeonUtility.hashesToLines(map);
        for (char[] chars : grid) {
            System.out.println(Arrays.toString(chars));
        }
        // do stuff with grid, it contains box drawing chars like ─ │ ┌ ┐ └ ┘ ├ ┤ ┬ ┴ ┼
    }
}
