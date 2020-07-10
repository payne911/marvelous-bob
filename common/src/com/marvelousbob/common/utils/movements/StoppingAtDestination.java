package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.Vector2;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// still [WIP]
@ToString
@NoArgsConstructor
public class StoppingAtDestination implements MovementStrategy {

    public static final float DEFAULT_MINIMUM_BOUND_CKECK = 1f;
    protected boolean arrivedAtDestination;
    private Vector2 dest;
    //    private float sign;
    private float minimumDistance;
    private MovementStrategy delegate;
    @Getter
    @Setter
    private Consumer<Vector2> onDestionationArrived;
    @Getter
    @Setter
    private Consumer<Vector2> onMoveUpdates;

    public StoppingAtDestination(Vector2 dest, Vector2 current,
            MovementStrategy delegate) {
        this(dest, current, DEFAULT_MINIMUM_BOUND_CKECK, delegate);
    }

    public StoppingAtDestination(Vector2 dest, Vector2 current, float minimumDistance,
            MovementStrategy delegate) {
        this.dest = Objects.requireNonNull(dest);
        this.delegate = Objects.requireNonNull(delegate);
        this.minimumDistance = minimumDistance;
//        this.sign = Math.signum(current.sub(dest).angle() - 180);
    }


    @Override
    public Vector2 move(Vector2 pos, float distance) {
        if (arrivedAtDestination) {
            return dest;
        }

        Vector2 newPos = delegate.move(pos, distance);

        if (newPos.equals(dest)) {
            onArrivedAtDestination();
            return dest;
        }

        // check if within destination bounds
        float newDist = newPos.dst(dest);
        if (newDist < minimumDistance) {
            onArrivedAtDestination();
            return dest;
        }

        // check if went to far: moved to the other side
        // does not really work... what are the maths for this ?
        // how to check if the vector has gone past the target ?
//        float newSign = Math.signum(newPos.sub(dest).angle() - 180);
//        if (sign != newSign) {
//            onArrivedAtDestination();
//            return dest;
//        }

        if (onMoveUpdates != null) {
            onMoveUpdates.accept(newPos);
        }
        return newPos;
    }


    private void onArrivedAtDestination() {
        this.arrivedAtDestination = true;
        if (onDestionationArrived != null) {
            onDestionationArrived.accept(dest);
        }
    }

    public void setDest(Vector2 dest, Vector2 current) {
        this.arrivedAtDestination = false;
//        this.sign = Math.signum(current.sub(dest).angle() - 180);
        this.dest = dest;
    }
}
