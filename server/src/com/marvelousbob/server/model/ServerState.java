package com.marvelousbob.server.model;

import com.badlogic.gdx.graphics.Color;
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.Player;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.EnemyCollisionDto;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.NewEnemyDto;
import com.marvelousbob.common.network.register.dto.PlayerUpdateDto;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import com.marvelousbob.common.state.GameWorldManager;
import com.marvelousbob.server.model.actions.Action;
import com.marvelousbob.server.worlds.LevelGenerator;
import com.marvelousbob.server.worlds.StaticSimpleLevelGenerator;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ServerState {

    public static final Color[] playerColors = {
            Color.WHITE,
            Color.GREEN,
            Color.RED,
            Color.MAGENTA,
            Color.YELLOW,
            Color.BROWN,
            Color.ORANGE,
            Color.TEAL
    };

    private long gameStateIndex;
    private int colorIndex;

    private GameWorldManager gameWorldManager;

    private Queue<Action> actions;

    private ArrayList<EnemyCollisionDto> enemyCollisions;
    private ArrayList<PlayerUpdateDto> playerUpdates;
    private ArrayList<NewEnemyDto> newEnemies;
    private ArrayList<PlayersBaseDto> basesHealth;
    private ArrayList<SpawnPointDto> spawnPointHealth;

    private LevelGenerator levelGenerator;

    public ServerState() {
        this.colorIndex = 0;
        this.gameStateIndex = 0;

        this.actions = new SynchronousQueue<>();
        this.gameWorldManager = new GameWorldManager(new GameWorld());
        this.levelGenerator = new StaticSimpleLevelGenerator();
        reset();
    }

    public void runGameLogic(float delta) {
        Action action;
        while ((action = actions.poll()) != null) {
            action.execute(this, delta);
        }
        gameWorldManager.updateGameState(delta);
    }


    public GameStateDto getCurrentGameStateAsDto() {
        return new GameStateDto(
                enemyCollisions,
                playerUpdates,
                newEnemies,
                basesHealth,
                spawnPointHealth,
                gameStateIndex++);
    }

    private void reset() {
        gameStateIndex = 0L;
        enemyCollisions = new ArrayList<>();
        playerUpdates = new ArrayList<>();
        newEnemies = new ArrayList<>();
        basesHealth = new ArrayList<>();
        spawnPointHealth = new ArrayList<>();
    }

    public boolean isEmptyRoom() {
        return gameWorldManager.getMutableGameWorld().getLocalGameState().getPlayers().isEmpty();
    }

    public void initializeOnFirstPlayerConnected() {
        // TODO: 2020-06-28 set level   ---OLA
    }

    public void addPlayer(Player player) {
        gameWorldManager.getMutableGameWorld().getLocalGameState().getPlayers()
                .put(player.getUuid(), player);
    }

    public Color getFreeColor() {
        if (colorIndex >= GameConstant.MAX_PLAYER_AMOUNT) {
            throw new RuntimeException("Max number of player reached");
        }
        return playerColors[colorIndex++];
    }

    public GameWorld getGameWorld() {
        return gameWorldManager.getMutableGameWorld();
    }

}
