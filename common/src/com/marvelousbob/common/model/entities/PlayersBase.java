package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
public class PlayersBase implements Drawable, Identifiable {

    private UUID uuid;
    private Polygon shape;
    private Color color;
    private float hp, maxHp;


    public PlayersBase(UUID uuid, Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.uuid = uuid;
    }

    public static PlayersBase hexagonalPlayerBase(UUID uuid, Vector2 center, float size,
            Color color) {
        Vector2 p1 = new Vector2(center.x + size, center.y);
        Vector2 p2 = p1.cpy().rotateAround(center, 120);
        Vector2 p3 = p2.cpy().rotateAround(center, 120);
        Vector2 p4 = p1.cpy().rotateAround(center, 60);
        Vector2 p5 = p2.cpy().rotateAround(center, 60);
        Vector2 p6 = p3.cpy().rotateAround(center, 60);
        float[] tVertices = new float[]{
                p1.x, p1.y,
                p4.x, p4.y,
                p2.x, p2.y,
                p5.x, p5.y,
                p3.x, p3.y,
                p6.x, p6.y
        };
        return new PlayersBase(uuid, new Polygon(tVertices), color);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.polygon(shape);
    }
}
