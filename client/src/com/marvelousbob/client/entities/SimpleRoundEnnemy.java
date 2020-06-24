package com.marvelousbob.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SimpleRoundEnnemy extends Ennemy {

    private Circle circle;
    private Color color;

    public SimpleRoundEnnemy(Circle circle, Color color) {
        this.circle = circle;
        this.color = color;
    }

    public SimpleRoundEnnemy(Vector2 center, float radius, Color color) {
        this(new Circle(center, radius), color);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.filledCircle(circle.x, circle.y, circle.radius);
    }
}
