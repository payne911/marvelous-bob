package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState implements Timestamped, Comparable<GameState> {

    private List<PlayerDto> playerDtos = new ArrayList<>(8); // todo: could be set as unordered?
    private long timestamp;

    @Override
    public int compareTo(GameState o) {
        return Long.compare(timestamp, o.timestamp);
    }


    private void addPlayer(PlayerDto playerDto) {
        playerDtos.add(playerDto);
    }
}