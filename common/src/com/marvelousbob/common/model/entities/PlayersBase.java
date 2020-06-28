package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class PlayersBase implements Drawable {

    private Rectangle rectangle; // todo: hexagon
    private Color color;

    public PlayersBase(float blX, float blY, float width, float height, Color color) {
        this(new com.badlogic.gdx.math.Rectangle(blX, blY, width, height), color);
    }

    public PlayersBase(float blX, float blY, float width, float height) {
        this(new com.badlogic.gdx.math.Rectangle(blX, blY, width, height), Color.FIREBRICK);
    }

    public PlayersBase(com.badlogic.gdx.math.Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public PlayersBase(com.badlogic.gdx.math.Rectangle rectangle) {
        this(rectangle, Color.FIREBRICK);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.rectangle(rectangle);
    }
}
