package com.marvelousbob.common.state;

import com.badlogic.gdx.utils.Array;
import com.marvelousbob.common.model.entities.Drawable;
import com.marvelousbob.common.model.entities.level.EnemySpawnPoint;
import com.marvelousbob.common.model.entities.level.PlayersBase;
import com.marvelousbob.common.model.entities.level.Wall;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Level implements Drawable {

    private long seed;
    private ConcurrentHashMap<UUID, PlayersBase> bases;
    private ConcurrentHashMap<UUID, EnemySpawnPoint> enemySpawnPoints;
    private ArrayList<Wall> walls;
    private int sizeX, sizeY, gridSize;


    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        walls.forEach(w -> w.drawMe(shapeDrawer));
        bases.values().forEach(b -> b.drawMe(shapeDrawer));
        enemySpawnPoints.values().forEach(es -> es.drawMe(shapeDrawer));
    }

    public void updateUsingPlayerBase(PlayersBaseDto playersBaseDto) {
        var base = bases.get(playersBaseDto.uuid);
        base.setHp(playersBaseDto.getHp());
    }

    public void updateUsingSpawnPoints(SpawnPointDto spawnPointDto) {
        var spawn = enemySpawnPoints.get(spawnPointDto.uuid);
        spawn.setHp(spawnPointDto.getHp());
    }

    public Array<PlayersBase> getAllPlayerBases() {
        var arr = new Array<PlayersBase>();
        bases.values().forEach(arr::add);
        return arr;
    }

    public Array<EnemySpawnPoint> getAllSpawnPoints() {
        var arr = new Array<EnemySpawnPoint>();
        enemySpawnPoints.values().forEach(arr::add);
        return arr;
    }
}