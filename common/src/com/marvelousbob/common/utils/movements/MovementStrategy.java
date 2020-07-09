package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * @param <T>
 */
public interface MovementStrategy<T extends Vector<T>> {


    /**
     * =================== = static creators = ===================
     */

    static <V extends Vector<V>> MovementStrategy<V> instant() {
        return new InstantMovement<>();
    }

    static <V extends Vector<V>> MovementStrategy<V> stayAt(V position) {
        return new StayAt<>(position);
    }

    static MovementStrategy<Vector2> constant(float angle) {
        return new ConstantSpeed(angle);
    }

    /**
     * todo
     *
     * @param pos      todo
     * @param distance todo
     * @return todo
     */
    T move(T pos, float distance);


}

