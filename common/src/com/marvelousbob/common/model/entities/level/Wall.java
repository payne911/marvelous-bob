package com.marvelousbob.common.model.entities.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.marvelousbob.common.model.entities.Drawable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Wall implements Drawable {

    private Rectangle rectangle;
    private Color color;

    public Wall(float blX, float blY, float width, float height, Color color) {
        this(new Rectangle(blX, blY, width, height), color);
    }

    public Wall(float blX, float blY, float width, float height) {
        this(new Rectangle(blX, blY, width, height), Color.BLACK);
    }

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
        shapeDrawer.filledRectangle(rectangle);
    }
}
