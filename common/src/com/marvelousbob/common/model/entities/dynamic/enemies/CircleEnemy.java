package com.marvelousbob.common.model.entities.dynamic.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
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

    private Circle circle;
    private Color color;

    public CircleEnemy(Circle circle, Color color) {
        this.circle = circle;
        this.color = color;
    }

    public CircleEnemy(Vector2 center, float radius, Color color) {
        this(new Circle(center, radius), color);
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
