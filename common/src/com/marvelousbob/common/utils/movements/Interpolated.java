package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class Interpolated implements MovementStrategy {

    /**
     * Optimisation to prevent interpolation from trying to calculate unnoticeable differences.
     * <p>
     * Increasing this value will increase the performance by reducing calculations.
     */
    private static final float SMALLEST_DELTA = 1;

    private Interpolation interpolation;
    private Vector2 destination;

    public Interpolated(Vector2 destination, Interpolation interpolation) {
        this.destination = Objects.requireNonNull(destination);
        this.interpolation = Objects.requireNonNull(interpolation);
    }

    private static boolean isBigEnoughDelta(float curr, float dest) {
        return Math.abs(curr - dest) > SMALLEST_DELTA;
    }

    @Override
    public Vector2 move(Vector2 pos, float distance) {
        float x = isBigEnoughDelta(pos.x, destination.x)
                ? interpolation.apply(pos.x, destination.x, distance)
                : pos.x;
        float y = isBigEnoughDelta(pos.y, destination.y)
                ? interpolation.apply(pos.y, destination.y, distance)
                : pos.y;
        return new Vector2(x, y);

    }

}
