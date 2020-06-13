package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.utils.Array;
import com.marvelousbob.common.network.register.Timestamped;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameState extends Timestamped implements Comparable<GameState> {

    private Array<Player> players = new Array<>(8); // todo: could be set as unordered?

    @Override
    public int compareTo(GameState o) {
        return (int) (this.timestamp - o.timestamp);
    }
}