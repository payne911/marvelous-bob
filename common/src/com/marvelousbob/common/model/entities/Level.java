package com.marvelousbob.common.model.entities;

import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import java.util.List;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Level implements Drawable {

    private final List<PlayersBase> base;
    private final List<EnemySpawnPoint> enemySpawnPoints;
    private final List<Wall> walls;


    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        base.forEach(b -> b.drawMe(shapeDrawer));
        walls.forEach(w -> w.drawMe(shapeDrawer));
        enemySpawnPoints.forEach(es -> es.drawMe(shapeDrawer));
    }

    public void updateUsingPlayerBase(PlayersBaseDto playersBaseDto) {
        // TODO
    }

    public void updateUsingSpawnPoints(SpawnPointDto spawnPointDto) {
        // TODO
    }

}
