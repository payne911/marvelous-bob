package com.marvelousbob.common.model.entities.dynamic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import lombok.NoArgsConstructor;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
public class SimpleRoundEnemy extends Enemy {

    private Circle circle;
    private Color color;

    public SimpleRoundEnemy(Circle circle, Color color) {
        this.circle = circle;
        this.color = color;
    }

    public SimpleRoundEnemy(Vector2 center, float radius, Color color) {
        this(new Circle(center, radius), color);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.filledCircle(circle.x, circle.y, circle.radius);
    }
}
