package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Rectangle;
import com.marvelousbob.common.utils.UUID;
import java.util.Objects;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TrianglePlayerBullet extends Bullet {

    /**
     * rectangle: bottom left + width and height
     */
    private Rectangle rectangle;

    public TrianglePlayerBullet(Rectangle rectangle) {
        super(UUID.getNext());
        this.rectangle = rectangle;
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        // no set colors, because drawMe is called by the player instance that shot that bullet.
        // The bullet will be the players color.
        shapeDrawer.filledRectangle(rectangle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrianglePlayerBullet that = (TrianglePlayerBullet) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
