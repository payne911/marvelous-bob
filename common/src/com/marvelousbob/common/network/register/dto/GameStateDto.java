package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.register.Timestamped;
import com.marvelousbob.common.utils.UUID;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public final class GameStateDto implements Dto, Timestamped,
        Comparable<GameStateDto> {

    private ConcurrentHashMap<UUID, PlayerDto> playersDtos;
    private ConcurrentHashMap<UUID, EnemyDto> enemiesDtos;
    private ConcurrentHashMap<UUID, Long> processedActionsByPlayer;
    private long timestamp;


    /**
     * Will call the other constructor with a {@code timestamp} value of {@link
     * System#currentTimeMillis()}.
     */
    public GameStateDto(ConcurrentHashMap<UUID, PlayerDto> playersDtos,
            ConcurrentHashMap<UUID, EnemyDto> enemiesDtos) {
        this(playersDtos, enemiesDtos, System.currentTimeMillis());
    }

    public GameStateDto(ConcurrentHashMap<UUID, PlayerDto> playersDtos,
            ConcurrentHashMap<UUID, EnemyDto> enemiesDtos, long timestamp) {
        this.playersDtos = playersDtos;
        this.enemiesDtos = enemiesDtos;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(GameStateDto o) {
        return Long.compare(timestamp, o.timestamp);
    }

    public void addPlayer(PlayerDto playerDto) {
        if (containsPlayerUuid(playerDto)) {
            log.warn("Overwriting a player who already had that UUID assigned in the GS.");
        }
        playersDtos.put(playerDto.getUuid(), playerDto);
    }

    public boolean containsPlayerUuid(PlayerDto playerDto) {
        return containsPlayerUuid(playerDto.getUuid());
    }

    public boolean containsPlayerUuid(UUID uuid) {
        return playersDtos.containsKey(uuid);
    }

    /**
     * @param inputDto the input comparing to
     * @return if all the values are the same (<b>without regards to timestamp</b>!)
     */
    public boolean isSameStateWithoutGameStateTime(GameStateDto inputDto) {
        if (playersDtos.size() != inputDto.playersDtos.size()) { // trivial case
            return false;
        }

        var inputEntries = inputDto.playersDtos.entrySet();
        for (var entry : inputEntries) { // checking if all color keys are the same
            boolean foundKey = inputDto.containsPlayerUuid(entry.getKey());
            if (!foundKey) {
                return false;
            }
            // todo: what about non-matching UUID despite same ColorIndex ?
        }

        for (var entry : inputEntries) { // checking if players match exactly
            PlayerDto inputPlayerDto = inputDto.playersDtos.get(entry.getKey());
            boolean foundMatch = playersDtos.get(inputPlayerDto.getUuid())
                    .hasAllMatchingFieldsExceptTimestamp(inputPlayerDto);
            if (!foundMatch) {
                return false;
            }
        }

        return true; // if there are no players, it's the same state
    }

    /**
     * Copies data from the input into the object which calls the function.
     */
    public void updateFromDto(GameStateDto otherGameStateDto) {
        compareAndRemoveDisconnectedPlayers(otherGameStateDto);

        for (var inputEntry : otherGameStateDto.playersDtos.entrySet()) {
            if (!playersDtos.containsKey(inputEntry.getKey())) { // checking if there's new player
                addPlayer(inputEntry.getValue()); // todo: should this be a deep copy?
            }

            // actually updating the state
            PlayerDto updatedPlayerDto = playersDtos.get(inputEntry.getKey());
            updatedPlayerDto.updateFromDto(inputEntry.getValue());
        }
    }

    public void compareAndRemoveDisconnectedPlayers(GameStateDto inputDto) {
        playersDtos.keySet().removeIf(uuid -> !inputDto.containsPlayerUuid(uuid));
    }

    public void removePlayer(UUID uuid) {
        if (!containsPlayerUuid(uuid)) {
            throw new MarvelousBobException("Trying to remove a player which isn't in the GS.");
        }

        var iterator = playersDtos.keySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(uuid)) {
                iterator.remove();
                break;
            }
        }
    }

    public Optional<PlayerDto> getPlayer(UUID uuid) {
        return Optional.ofNullable(playersDtos.get(uuid));
    }

    /**
     * @deprecated This method is slower than another alternative. Consider using
     * `MyGame.controller.getSelfPlayer().getUuid()` instead.
     */
    @Deprecated
    public Optional<PlayerDto> findPlayerUsingColorIndex(int colorIndex) {
        return playersDtos.values().stream()
                .filter(p -> p.getColorIndex() == colorIndex)
                .findAny();
    }
}