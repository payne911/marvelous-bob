package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector;

public class InstantMovement<T extends Vector<T>> implements MovementStrategy<T> {

    @Override
    public T move(T pos, float distance) {
        return pos;
    }
}
