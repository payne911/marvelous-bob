package com.marvelousbob.common.utils.movements;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.utils.MovementUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class ConstantSpeed implements MovementStrategy {

    @Setter
    private float angle;

    public ConstantSpeed(float angle) {
        this.angle = angle;
    }

    public ConstantSpeed(Vector2 origin, Vector2 destination) {
        this.angle = MathUtils.atan2(destination.y - origin.y, destination.x - origin.x);
    }

    @Override
    public Vector2 move(Vector2 pos, float distance) {
        return MovementUtils.constantSpeed(pos, distance, angle);
    }

    public void set(Vector2 origin, Vector2 destination) {
        this.angle = MathUtils.atan2(destination.y - origin.y, destination.x - origin.x);
    }

}
