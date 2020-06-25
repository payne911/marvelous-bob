package com.marvelousbob.server.model;

import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.*;
import com.marvelousbob.common.utils.MovementUtils;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.server.model.actions.Action;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Data
public class ServerState {

    /**
     * Index for the incremental game states
     */
    private long index;


    /**
     * Accumulates actions to be performed between server ticks.
     */
    private Queue<Action> actions;


    /**
     * All players registered for this game
     */
    private ConcurrentHashMap<UUID, PlayerDto> players;


    /**
     * Color indices not yet assigned to a player
     */
    private Set<Integer> freePlayerColorIds;


    private ConcurrentHashMap<UUID, Long> processedActionsByPlayer;


    public ServerState() {
        this.actions = new PriorityBlockingQueue<>();
        this.players = new ConcurrentHashMap<>();
        this.freePlayerColorIds = IntStream.range(0, GameConstant.MAX_PLAYER_AMOUNT).boxed()
                .collect(Collectors.toSet());
        this.processedActionsByPlayer = new ConcurrentHashMap<>();
    }


    public Optional<PlayerDto> getPlayer(UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }


    public void addAction(Action action) {
        actions.offer(action);
    }


    public void addPlayer(PlayerDto playerDto) {
        if (players.containsKey(playerDto.getUuid())) {
            log.warn("Overwriting a player who already had that colorIndex assigned in the GS.");
        }
        players.put(playerDto.getUuid(), playerDto);
    }


    public void removePlayer(UUID uuid) {
        var iterator = players.entrySet().iterator();
        while (iterator.hasNext()) {
            UUID entryUuid = iterator.next().getValue().getUuid();
            if (entryUuid.equals(uuid)) {
                iterator.remove();
                break;
            }
        }
    }


    public void executeAll(float delta) {
        Action action;
        while ((action = actions.poll()) != null) {
            action.execute(this, delta);
            processedActionsByPlayer.put(action.getPlayerId(), action.getIndex());
        }
    }


    public Optional<IndexedGameStateDto> update(float delta) {
        boolean hasMoved = MovementUtils.interpolatePlayers(players.values(), delta);
        if (hasMoved) {
            GameStateDto gameStateDto = new GameStateDto(players, System.currentTimeMillis());
            return Optional.of(new IndexedGameStateDto(gameStateDto, index++));
        }
        return Optional.empty();
    }


    public GameInitializationDto getInitializationDto(UUID playerUuid) {
        GameInitializationDto gameInit = new GameInitializationDto();
        GameStateDto gameState = new GameStateDto(players);
        gameInit.setFirstGameStateDto(gameState);
        gameInit.setCurrentPlayerId(playerUuid);
//        gameInit.setFirstLevel();
        return gameInit;
    }

    /**
     * To obtain a colorIndex (<b><i>not {@link UUID}</i></b>) which is free, starting from 0, and
     * iterating through the players.
     *
     * @return a free colorIndex to assign to a new {@link PlayerDto}
     * @throws MarvelousBobException when no free spot was available ({@value GameConstant#MAX_PLAYER_AMOUNT}
     *                               players max)
     * @see GameConstant#MAX_PLAYER_AMOUNT
     */
    public int getFreeId() {
        Integer i = freePlayerColorIds.stream().findAny().orElseThrow(() ->
                new MarvelousBobException(
                        "Could not find an available Color Index: the room must be full."));
        freePlayerColorIds.remove(i);
        return i;
    }

    public void updateProcessedAction(UUID playerId, IndexedDto<?> dto) {
        processedActionsByPlayer.put(playerId, dto.getIndex());
    }
}
