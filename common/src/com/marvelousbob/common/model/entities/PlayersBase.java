package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
public class PlayersBase implements Drawable, Identifiable {

    private UUID uuid;
    private Rectangle rectangle; // todo: hexagon
    private Color color;
    private float hp, maxHp;

    public PlayersBase(UUID uuid, float blX, float blY, float width, float height, Color color) {
        this(uuid, new Rectangle(blX, blY, width, height), color);
    }

    public PlayersBase(UUID uuid, float blX, float blY, float width, float height) {
        this(uuid, new Rectangle(blX, blY, width, height), Color.FIREBRICK);
    }

    public PlayersBase(UUID uuid, Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public PlayersBase(UUID uuid, Rectangle rectangle) {
        this(uuid, rectangle, Color.FIREBRICK);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.rectangle(rectangle);
    }
}
