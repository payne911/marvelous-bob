package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class GameStateDto extends IndexedDto<GameStateDto> implements Timestamped, Comparable<GameStateDto> {

    private List<PlayerDto> playerDtos = new ArrayList<>(8); // todo: could be set as unordered?
    private long timestamp;

    public GameStateDto(GameStateDto dto, long index) {
        super(dto, index);
    }

    @Override
    public int compareTo(GameStateDto o) {
        return Long.compare(timestamp, o.timestamp);
    }


    private void addPlayer(PlayerDto playerDto) {
        playerDtos.add(playerDto);
    }
}