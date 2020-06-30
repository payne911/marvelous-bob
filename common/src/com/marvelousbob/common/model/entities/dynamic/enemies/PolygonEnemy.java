package com.marvelousbob.common.model.entities.dynamic.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
public class PolygonEnemy extends Enemy {

    private final Polygon polyShape = new Polygon(VERTICES);
    private static final float[] VERTICES = new float[]{
            0, 0,
            16, 32,
            32, 0,
            16, 10
    };

    private Color color;

    public PolygonEnemy(Vector2 center, float scale, Color color) {
        this.color = color;
        polyShape.setPosition(center.x, center.y);
        setScale(scale);
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
