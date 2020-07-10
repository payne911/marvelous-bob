package com.marvelousbob.common.model.entities.dynamic.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.common.utils.movements.MovementStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class CircleEnemy extends Enemy {

    public static final Color DEFAULT_COLOR = Color.CHARTREUSE.cpy();
    private Circle circle;
    private Color color;

    public CircleEnemy(UUID uuid, UUID spawnPointUuid, MovementStrategy moveStrat,
            Circle circle, Color color) {
        super(uuid, spawnPointUuid, moveStrat);
        this.circle = circle;
        this.color = color;
    }

    public CircleEnemy(UUID uuid, UUID spawnPointUuid, MovementStrategy moveStrat,
            Circle circle) {
        this(uuid, spawnPointUuid, moveStrat, circle, DEFAULT_COLOR.cpy());
    }

    public CircleEnemy(UUID uuid, UUID spawnPointUuid, MovementStrategy moveStrat,
            Vector2 center, float radius, Color color) {
        this(uuid, spawnPointUuid, moveStrat, new Circle(center, radius), color);
    }

    public CircleEnemy(UUID uuid, UUID spawnPointUuid, MovementStrategy moveStrat,
            Vector2 center, float radius) {
        this(uuid, spawnPointUuid, moveStrat, new Circle(center, radius), DEFAULT_COLOR.cpy());
    }

    @Override
    public float getCurrCenterX() {
        return circle.x;
    }

    @Override
    public float getCurrCenterY() {
        return circle.y;
    }

    @Override
    public void setCurrCenterX(float x) {
        circle.setPosition(x, getCurrCenterY());
    }

    @Override
    public void setCurrCenterY(float y) {
        circle.setPosition(getCurrCenterX(), y);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.filledCircle(circle.x, circle.y, circle.radius);
    }
}
