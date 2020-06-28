package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnemySpawnPoint implements Drawable, Identifiable {

    private UUID uuid;
    private Vector2 pos;
    private float hp, maxHp;
    private Polygon shape;
    private Color color;

    public EnemySpawnPoint(UUID uuid, Polygon shape, Color color) {
        this.uuid = uuid;
        this.shape = shape;
        this.color = color;
    }

    public static EnemySpawnPoint starShaped(UUID uuid, Vector2 center, float size, Color color) {
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
        return new EnemySpawnPoint(uuid, new Polygon(tVertices), color);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.filledPolygon(shape);
    }
}
