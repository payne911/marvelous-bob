package com.marvelousbob.common.state;

import com.marvelousbob.common.model.entities.Drawable;
import com.marvelousbob.common.model.entities.level.EnemySpawnPoint;
import com.marvelousbob.common.model.entities.level.PlayersBase;
import com.marvelousbob.common.model.entities.level.Wall;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import java.util.List;
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

    public void updateUsingPlayerBase(PlayersBaseDto playersBaseDto, LocalGameState gameState) {
        var base = bases.get(playersBaseDto.uuid);
        base.setHp(playersBaseDto.getHp());
        playersBaseDto.enemiesToRemove.forEach(gameState::removeEnemy);
    }

    public void updateUsingSpawnPoints(SpawnPointDto spawnPointDto) {
        var spawn = enemySpawnPoints.get(spawnPointDto.uuid);
        spawn.setHp(spawnPointDto.getHp());
    }

    public List<PlayersBase> getAllPlayerBases() {
        return new ArrayList<>(bases.values());
    }

    public List<EnemySpawnPoint> getAllSpawnPoints() {
        return new ArrayList<>(enemySpawnPoints.values());
    }
}