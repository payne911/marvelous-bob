package com.marvelousbob.server.model;

import com.badlogic.gdx.graphics.Color;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.network.register.dto.EnemyCollisionDto;
import com.marvelousbob.common.network.register.dto.GameInitializationDto;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.NewEnemyDto;
import com.marvelousbob.common.network.register.dto.PlayerUpdateDto;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.worlds.LevelGenerator;
import com.marvelousbob.server.worlds.ProceduralLevelGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ServerState {

    public static final Color[] PLAYER_COLORS = {
            Color.WHITE.cpy(),
            Color.GREEN.cpy(),
            Color.RED.cpy(),
            Color.MAGENTA.cpy(),
            Color.YELLOW.cpy(),
            Color.BROWN.cpy(),
            Color.ORANGE.cpy(),
            Color.TEAL.cpy()
    };

    public static final short MAX_PLAYER_AMOUNT = 8;

    private Map<UUID, Integer> playersColorId;

    private long gameStateIndex;
    private int colorIndex;

    private ServerWorldManager serverWorldManager;

//    private Queue<Action> actions;

    private ArrayList<EnemyCollisionDto> enemyCollisions;
    private ConcurrentHashMap<UUID, PlayerUpdateDto> playerUpdates;
    private ArrayList<NewEnemyDto> newEnemies;
    private ArrayList<PlayersBaseDto> basesHealth;
    private ArrayList<SpawnPointDto> spawnPointHealth;

    private LevelGenerator levelGenerator;

    public ServerState() {
        this.playersColorId = new ConcurrentHashMap<>(MAX_PLAYER_AMOUNT);
//        this.actions = new SynchronousQueue<>();
        this.serverWorldManager = new ServerWorldManager(new GameWorld());
//        this.levelGenerator = new StaticSimpleLevelGenerator();
        this.levelGenerator = new ProceduralLevelGenerator();
        reset();
    }

    public void runGameLogic(float delta) {
//        Action action;
//        while ((action = actions.poll()) != null) {
//            action.execute(this, delta);
//        }
        if (isLevelInitialized()) {
            serverWorldManager.updateGameState(delta);
        }
    }

    private boolean isLevelInitialized() {
        return getServerWorldManager().getMutableGameWorld().getLevel() != null;
    }

    public GameStateDto getCurrentGameStateAsDto() {
        var newEnemyDto = serverWorldManager.extractNewEnemies().stream()
                .map(NewEnemyDto::new)
                .collect(Collectors.toList());
        return new GameStateDto(
                enemyCollisions,
                new ArrayList<>(playerUpdates.values()),
                new ArrayList<>(newEnemyDto),
                basesHealth,
                spawnPointHealth,
                gameStateIndex++);
    }

    public void reset() {
        resetLists();
    }

    /**
     * Should bring back the ServerState to its original state (all attributes are reset).
     */
    public void completeReset() {
        colorIndex = 0;
        gameStateIndex = 0;
        initialize();
    }

    public void initialize() {
        this.playersColorId = new ConcurrentHashMap<>(MAX_PLAYER_AMOUNT);
//        this.actions = new SynchronousQueue<>();
        this.serverWorldManager = new ServerWorldManager(new GameWorld());
//        this.levelGenerator = new StaticSimpleLevelGenerator();
        this.levelGenerator = new ProceduralLevelGenerator();
        reset();
    }

    public void resetLists() {
        enemyCollisions = new ArrayList<>();
        playerUpdates = new ConcurrentHashMap<>();
        newEnemies = new ArrayList<>();
        basesHealth = new ArrayList<>();
        spawnPointHealth = new ArrayList<>();
    }

    public boolean isEmptyRoom() {
        return serverWorldManager.getMutableGameWorld().getLocalGameState().hasNoPlayers();
    }

    public boolean hasOnePlayer() {
        return serverWorldManager.getMutableGameWorld().getLocalGameState().hasOnePlayer();
    }

    public synchronized void initializeOnFirstPlayerConnected() {
        newLevel();
    }

    public synchronized void newLevel() {
        var generatedLevel = levelGenerator.getLevel();
        generatedLevel.getAllSpawnPoints().forEach(sp -> sp.findPathToBase(generatedLevel));
        serverWorldManager.getMutableGameWorld().setLevel(generatedLevel);
    }

    public synchronized Collection<Player> getPlayers() {
        return serverWorldManager.getMutableGameWorld().getLocalGameState().getPlayersList();
    }

    public synchronized void addPlayer(Player<?> player) {
        serverWorldManager.getMutableGameWorld().getLocalGameState().getPlayers()
                .put(player.getUuid(), player);
    }

    public synchronized void removePlayer(UUID playerUuid) {
        serverWorldManager.getMutableGameWorld().getLocalGameState().getPlayers()
                .remove(playerUuid);
    }

    public Color getFreeColor(UUID uuid) throws MarvelousBobException {
        return PLAYER_COLORS[extractFreeColorId(uuid)];
    }

    public int extractFreeColorId(UUID uuid) throws MarvelousBobException {
        var ints = IntStream.range(0, MAX_PLAYER_AMOUNT).boxed().collect(Collectors.toSet());
        int colorId = ints.stream().filter(i -> !playersColorId.containsValue(i)).findAny()
                .orElseThrow(() ->
                        new MarvelousBobException(
                                "Could not find an available Color Index: the room must be full."));
        playersColorId.put(uuid, colorId);
        return colorId;
    }

    public void freePlayerColorId(UUID uuid) {
        playersColorId.remove(uuid);
    }

    public GameInitializationDto getGameInitDto(UUID currentPlayerUuid) {
        var world = serverWorldManager.getMutableGameWorld();
        return new GameInitializationDto(world, world.getLevel().getSeed(), currentPlayerUuid);
    }

    public void updatePlayerPos(MoveActionDto moveAction) {
        serverWorldManager.getMutableGameWorld().getLocalGameState()
                .updateUsingMoveAction(moveAction);
    }

    public void updatePlayerFacingAngle(UUID playerUuid, float angle) {
        serverWorldManager.getMutableGameWorld().getLocalGameState().getRangedPlayerById(playerUuid)
                .ifPresent(p -> p.setMouseAngleRelativeToCenter(angle));

        var playerUpdate = playerUpdates.get(playerUuid);
        if (playerUpdate == null) {
            AtomicReference<Float> health = new AtomicReference<>();
            serverWorldManager.getMutableGameWorld().getLocalGameState().getPlayer(playerUuid)
                    .ifPresent(
                            p -> health.set(p.getHp()));
            playerUpdates
                    .put(playerUuid, new PlayerUpdateDto(playerUuid, health.get(), angle, false));
        } else {
            playerUpdate.setAngle(angle);
        }
    }
}
