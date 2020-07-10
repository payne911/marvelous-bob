package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector2;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class StayAt implements MovementStrategy {

    private Vector2 stayAt;

    public StayAt(Vector2 stayAt) {
        this.stayAt = stayAt;
    }

    @Override
    public Vector2 move(Vector2 pos, float distance) {
        return stayAt;
    }
}
