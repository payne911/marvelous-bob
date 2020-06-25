package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.utils.UUID;
import java.util.Objects;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TrianglePlayerBullet extends Bullet {

    /**
     * rectangle: bottome left + width and height
     */
    private Rectangle rectangle;
    private Vector2 direction;
    private UUID id;

    public TrianglePlayerBullet(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.id = UUID.getNext();
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        // no set colors, because drawMe is called by the player instance that shot that bullet.
        // The bullet will be the players color.
        shapeDrawer.filledRectangle(rectangle);
    }

    @Override
    public UUID getUuid() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrianglePlayerBullet that = (TrianglePlayerBullet) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
