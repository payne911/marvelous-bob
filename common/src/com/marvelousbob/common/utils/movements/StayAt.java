package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector;

public class StayAt<T extends Vector<T>> implements MovementStrategy<T> {

    private T stayAt;

    public StayAt(T stayAt) {
        this.stayAt = stayAt;
    }

    @Override
    public T move(T pos, float distance) {
        return stayAt;
    }
}
