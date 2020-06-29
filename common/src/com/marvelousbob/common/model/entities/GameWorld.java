package com.marvelousbob.common.model.entities;

import com.marvelousbob.common.model.entities.level.Level;
import com.marvelousbob.common.network.register.dto.EnemyCollisionDto;
import com.marvelousbob.common.network.register.dto.NewEnemyDto;
import com.marvelousbob.common.network.register.dto.PlayerUpdateDto;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import com.marvelousbob.common.state.LocalGameState;
import com.marvelousbob.common.utils.MovementUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class GameWorld implements Drawable {

    private LocalGameState localGameState;
    private Level level;

    public GameWorld() {
        this.level = new Level(new ConcurrentHashMap<>(), new ConcurrentHashMap<>(),
                new ArrayList<>());
        this.localGameState = new LocalGameState(new HashMap<>(), new ConcurrentHashMap<>(),
                new ConcurrentHashMap<>());
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        level.drawMe(shapeDrawer);
        localGameState.drawMe(shapeDrawer);
    }

    public void updatePlayer(PlayerUpdateDto playerUpdateDto) {
        localGameState.updateUsingPlayerList(playerUpdateDto);
    }

    public void updatePlayerBase(PlayersBaseDto playersBaseDto) {
        level.updateUsingPlayerBase(playersBaseDto);
    }

    public void updateSpawnPoints(SpawnPointDto spawnPointDto) {
        level.updateUsingSpawnPoints(spawnPointDto);
    }

    public void updateEnemyCollision(EnemyCollisionDto enemyCollisionDto) {
        localGameState.updateUsingEnemyCollision(enemyCollisionDto);
    }

    public void updateNewEnemy(NewEnemyDto newEnemyDto) {
        localGameState.updateNewEnemy(newEnemyDto);
    }

    public void interpolatePlayerPositions(float delta) {
        MovementUtils.interpolatePlayers(localGameState.getPlayers().values(), delta);
    }
}
