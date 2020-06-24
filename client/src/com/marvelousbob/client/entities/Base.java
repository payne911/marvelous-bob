package com.marvelousbob.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Base implements Drawable {

    private Rectangle rectangle; // todo: hexagon
    private Color color;

    public Base(float blX, float blY, float width, float height, Color color) {
        this(new com.badlogic.gdx.math.Rectangle(blX, blY, width, height), color);
    }

    public Base(float blX, float blY, float width, float height) {
        this(new com.badlogic.gdx.math.Rectangle(blX, blY, width, height), Color.FIREBRICK);
    }

    public Base(com.badlogic.gdx.math.Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Base(com.badlogic.gdx.math.Rectangle rectangle) {
        this(rectangle, Color.FIREBRICK);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.rectangle(rectangle);
    }
}
