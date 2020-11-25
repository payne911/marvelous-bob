package com.marvelousbob.common.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
        enemySpawnPoints.values().forEach(es -> es.drawMe(shapeDrawer));
        bases.values().forEach(b -> {
            b.drawMe(shapeDrawer);

            // temporary health bar
            shapeDrawer.setColor(Color.BLACK);
            float posX = Gdx.graphics.getWidth() - 22f;
            float posY = 12f;
            float fullHeight = Gdx.graphics.getHeight() - 25f;
            float borderMargin = 2f;
            shapeDrawer.rectangle(posX, posY, 15f, fullHeight, borderMargin * 2f);
            shapeDrawer.setColor(Color.FIREBRICK);
            float hpPercent = Math.max(0, (b.getHp()/b.getMaxHp()) * (fullHeight - borderMargin  * 2f));
            shapeDrawer.filledRectangle(posX + borderMargin, posY + borderMargin, 11f, hpPercent);
        });
    }

    public void updatePlayerBase(PlayersBaseDto playersBaseDto, LocalGameState gameState) {
        var base = bases.get(playersBaseDto.uuid);
        base.setHp(playersBaseDto.getHp());
    }

    public void removeEnemiesWithPlayerBaseDto(PlayersBaseDto playersBaseDto, LocalGameState gameState) {
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