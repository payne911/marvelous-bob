package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector2;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class InstantMovement implements MovementStrategy {

    @Override
    public Vector2 move(Vector2 pos, float distance) {
        return pos.cpy();
    }
}
