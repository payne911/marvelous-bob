package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.utils.Array;
import com.marvelousbob.common.network.register.Timestamped;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class GameState implements Timestamped, Comparable<GameState> {

    private Array<Player> players = new Array<>(8); // todo: could be set as unordered?
    private long timestamp;

    @Override
    public int compareTo(GameState o) {
        return Long.compare(timestamp, o.timestamp);
    }


    private void addPlayer(Player player) {
        players.add(player);
    }
}