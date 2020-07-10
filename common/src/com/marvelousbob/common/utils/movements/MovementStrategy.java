package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector2;

public interface MovementStrategy {


    /**
     * =================== = static creators = ===================
     */

    static MovementStrategy instant() {
        return new InstantMovement();
    }

    static MovementStrategy stayAt(Vector2 position) {
        return new StayAt(position);
    }

    static MovementStrategy constant(float angle) {
        return new ConstantSpeed(angle);
    }

    /**
     * todo
     *
     * @param pos      todo
     * @param distance todo
     * @return todo
     */
    Vector2 move(Vector2 pos, float distance);
}

