package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.register.Timestamped;
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

    private ConcurrentHashMap<Integer, PlayerDto> playersDtos;
    private ConcurrentHashMap<UUID, Long> processedActionsByPlayer;
    private long timestamp;


    public GameStateDto(ConcurrentHashMap<Integer, PlayerDto> playersDtos) {
        this(playersDtos, System.currentTimeMillis());
    }

    public GameStateDto(ConcurrentHashMap<Integer, PlayerDto> playersDtos, long timestamp) {
        this.playersDtos = playersDtos;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(GameStateDto o) {
        return Long.compare(timestamp, o.timestamp);
    }

    public void addPlayer(PlayerDto playerDto) {
        if (containsColor(playerDto)) {
            log.warn("Overwriting a player who already had that colorIndex assigned in the GS.");
        }
        playersDtos.put(playerDto.getColorIndex(), playerDto);
    }

    public boolean containsColor(PlayerDto playerDto) {
        return playersDtos.containsKey(playerDto.getColorIndex());
    }

    public boolean containsColor(Integer colorIndex) {
        return playersDtos.containsKey(colorIndex);
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
            boolean foundKey = inputDto.containsColor(entry.getKey());
            if (!foundKey) {
                return false;
            }
            // todo: what about non-matching UUID despite same ColorIndex ?
        }

        for (var entry : inputEntries) { // checking if players match exactly
            PlayerDto inputPlayerDto = inputDto.playersDtos.get(entry.getKey());
            boolean foundMatch = playersDtos.get(inputPlayerDto.getColorIndex())
                    .hasAllMatchingFieldsExceptTimeAndColorIndex(inputPlayerDto);
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
        playersDtos.keySet().removeIf(colorIndex -> !inputDto.containsColor(colorIndex));
    }

    public void removePlayer(UUID uuid) {
        var iterator = playersDtos.entrySet().iterator();
        while (iterator.hasNext()) {
            UUID entryUuid = iterator.next().getValue().getUuid();
            if (entryUuid.getStringId().equals(uuid.getStringId())) {
                iterator.remove();
                break;
            }
        }
    }

    public void removePlayer(int colorIndex) {
        if (!containsColor(colorIndex)) {
            throw new MarvelousBobException("Trying to remove a player which isn't in the GS.");
        }

        var iterator = playersDtos.keySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == colorIndex) {
                iterator.remove();
                break;
            }
        }
    }

    public Optional<PlayerDto> getPlayer(int colorIndex) {
        return Optional.ofNullable(playersDtos.get(colorIndex));
    }

    public Optional<PlayerDto> getPlayer(UUID uuid) {
        return playersDtos.values().stream()
                .filter(p -> p.getUuid().getStringId().equals(uuid.getStringId()))
                .findAny();
    }


}