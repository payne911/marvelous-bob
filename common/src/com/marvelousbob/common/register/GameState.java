package com.marvelousbob.common.register;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameState extends Timestamped implements Comparable<GameState> {

    private float p1x, p1y, p2x, p2y;

    @Override
    public int compareTo(GameState o) {
        return (int) (this.timestamp - o.timestamp);
    }
}