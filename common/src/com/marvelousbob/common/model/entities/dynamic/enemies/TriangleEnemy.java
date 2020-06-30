package com.marvelousbob.common.model.entities.dynamic.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import lombok.NoArgsConstructor;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
public class TriangleEnemy extends Enemy {

    private Vector2 currCenterPos;
    private Color color;

    public TriangleEnemy(Vector2 center, float size, Color color) {
        this.color = color;
        this.currCenterPos = center;
    }

    @Override
    public float getCurrCenterX() {
        return currCenterPos.x;
    }

    @Override
    public float getCurrCenterY() {
        return currCenterPos.y;
    }

    @Override
    public void setCurrCenterX(float x) {
        currCenterPos.x = x;
    }

    @Override
    public void setCurrCenterY(float y) {
        currCenterPos.y = y;
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
//        shapeDrawer.triangle(); todo
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
