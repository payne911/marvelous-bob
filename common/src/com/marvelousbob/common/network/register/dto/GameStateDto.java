package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.Timestamped;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class GameStateDto extends IndexedDto<GameStateDto> implements Timestamped,
        Comparable<GameStateDto> {

    private final ArrayList<PlayerDto> playerDtos = new ArrayList<>(GameConstant.MAX_PLAYER_AMOUNT);
    private long timestamp;

    public GameStateDto(GameStateDto dto, long index) {
        super(dto, index);
    }

    @Override
    public int compareTo(GameStateDto o) {
        return Long.compare(timestamp, o.timestamp);
    }

    public void addPlayer(PlayerDto playerDto) {
        playerDtos.add(playerDto);
    }

    /**
     * @param gameStateDto the input comparing to
     * @return if all the values are the same (<b>without regards to timestamp</b>!)
     */
    public boolean isSameStateWithoutGameStateTime(GameStateDto gameStateDto) {
        // todo: O(n^2) is bad... maybe change to HashMap ?
        for (var player : playerDtos) {
            boolean foundMatch = gameStateDto.getPlayerDtos().stream()
                    .anyMatch(player::isSameStateSamePlayerWithoutTime);
            if (!foundMatch) {
                return false;
            }
        }
        return true; // if there are no players, it's the same state
    }

    public void updateFromDto(GameStateDto inputDto) {

    }

    /**
     * To obtain an ID (<b><i>not {@link UUID}</i></b>) which is free, starting from 0, and
     * iterating through the players.
     *
     * @return a free ID to assign to a new {@link PlayerDto}
     * @throws MarvelousBobException when no free spot was available ({@value GameConstant#MAX_PLAYER_AMOUNT}
     *                               players max)
     * @see GameConstant#MAX_PLAYER_AMOUNT
     */
    public int getFreeId() throws MarvelousBobException {
        final Set<Integer> takenIds = playerDtos.stream()
                .map(PlayerDto::getColorIndex)
                .collect(Collectors.toSet());
        return IntStream.range(0, GameConstant.MAX_PLAYER_AMOUNT)
                .filter(id -> !takenIds.contains(id))
                .findFirst()
                .orElseThrow(() -> new MarvelousBobException(
                        "Could not find an available Color Index: the room must be full."));
    }
}