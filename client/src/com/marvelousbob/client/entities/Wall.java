package com.marvelousbob.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Wall implements Drawable {

    private Rectangle rectangle;
    private Color color;

    public Wall(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Wall(Rectangle rectangle) {
        this(rectangle, Color.BLACK);
    }


    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.rectangle(rectangle);
    }
}
