package com.marvelousbob.common.model.entities.dynamic.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.common.utils.movements.MovementStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PolygonEnemy extends Enemy {

    public static final Color DEFAULT_COLOR = Color.GOLDENROD.cpy();
    private static final float HEIGHT = 20;
    private static final float HALF_HEIGHT = HEIGHT / 2;
    private static final float WIDTH = 14;
    private static final float HALF_WIDTH = WIDTH / 2;
    private final Polygon polyShape = new Polygon(VERTICES);
    private static final float[] VERTICES = new float[]{
            -HALF_WIDTH, -HALF_HEIGHT,
            0, HALF_HEIGHT,
            HALF_WIDTH, -HALF_HEIGHT,
            0, -HEIGHT / 4
    };

    private Color color;

    public PolygonEnemy(UUID uuid, UUID spawnPoint, MovementStrategy moveStrat,
            Vector2 center, float scale, Color color) {
        super(uuid, spawnPoint, moveStrat);
        this.color = color;
        polyShape.setPosition(center.x, center.y);
        setScale(scale);
    }

    public PolygonEnemy(UUID uuid, UUID spawnPoint, MovementStrategy moveStrat,
            Vector2 center, Color color) {
        this(uuid, spawnPoint, moveStrat, center, 1, color);
    }

    public PolygonEnemy(UUID uuid, UUID spawnPoint, MovementStrategy moveStrat,
            Vector2 center, float scale) {
        this(uuid, spawnPoint, moveStrat, center, scale, DEFAULT_COLOR.cpy());
    }

    public PolygonEnemy(UUID uuid, UUID spawnPoint, MovementStrategy moveStrat,
            Vector2 center) {
        this(uuid, spawnPoint, moveStrat, center, 1, DEFAULT_COLOR.cpy());
    }

    @Override
    public float getCurrCenterX() {
        return polyShape.getX(); // todo: is this the center?
    }

    @Override
    public float getCurrCenterY() {
        return polyShape.getY(); // todo: is this the center?
    }

    @Override
    public void setCurrCenterX(float x) {
        polyShape.setPosition(x, getCurrCenterY());
    }

    @Override
    public void setCurrCenterY(float y) {
        polyShape.setPosition(getCurrCenterX(), y);
    }

    public void setScale(float scale) {
        polyShape.setScale(scale, scale);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.polygon(polyShape);
    }
}
