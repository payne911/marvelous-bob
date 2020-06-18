package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.Timestamped;
import java.util.ArrayList;
import java.util.List;
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

    private List<PlayerDto> playerDtos = new ArrayList<>(GameConstant.MAX_PLAYER_AMOUNT);
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