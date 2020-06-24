package com.marvelousbob.client.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class EnnemySpawn implements Drawable {

    public Polygon shape;
    public Color color;

    public EnnemySpawn(Polygon shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public static EnnemySpawn starShaped(Vector2 center, float size, Color color) {
        Vector2 p1 = new Vector2(center.x + size, center.y);
        Vector2 p2 = p1.cpy().rotateAround(center, 120);
        Vector2 p3 = p2.cpy().rotateAround(center, 120);
        float[] t1vertices = new float[]{p1.x, p1.y, p2.x, p2.y, p3.x, p3.y};
        Polygon triangle2 = new Polygon(new float[]{p1.x, p1.y, p2.x, p2.y, p3.x, p3.y});
        triangle2.rotate(180);
        float[] t2Vertices = triangle2.getTransformedVertices();
        float[] starVertices = new float[12];
        for (int i = 0; i < t1vertices.length; i++) {
            starVertices[i] = t1vertices[i];
        }
        for (int i = 0; i < t2Vertices.length; i++) {
            starVertices[i + t1vertices.length] = t2Vertices[i];
        }
        return new EnnemySpawn(new Polygon(starVertices), color);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.filledPolygon(shape);
    }
}
