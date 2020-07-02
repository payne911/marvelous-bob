package com.marvelousbob.common.model.entities.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.entities.Drawable;
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
    private Polygon shape2;
    private Color color;

    public EnemySpawnPoint(UUID uuid, Polygon shape, Polygon shape2, Color color) {
        this.uuid = uuid;
        this.shape = shape;
        this.shape2 = shape2;
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
                p2.x, p2.y,
                p3.x, p3.y,
        };

        float[] t2Vertices = new float[]{
                p4.x, p4.y,
                p5.x, p5.y,
                p6.x, p6.y,
        };
        return new EnemySpawnPoint(uuid, new Polygon(tVertices), new Polygon(t2Vertices), color);
    }

    public static EnemySpawnPoint starShaped(UUID uuid, Vector2 center, float size) {
        return starShaped(uuid, center, size, Color.BLUE);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        float[] t1 = shape.getVertices();
        float[] t2 = shape2.getVertices();
        shapeDrawer.setColor(color);
        shapeDrawer.filledTriangle(t1[0], t1[1], t1[2], t1[3], t1[4], t1[5]);
        shapeDrawer.filledTriangle(t2[0], t2[1], t2[2], t2[3], t2[4], t2[5]);
    }
}
