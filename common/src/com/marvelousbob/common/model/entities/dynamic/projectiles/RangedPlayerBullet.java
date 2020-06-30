package com.marvelousbob.common.model.entities.dynamic.projectiles;

import com.badlogic.gdx.math.Rectangle;
import com.marvelousbob.common.model.entities.dynamic.Bullet;
import com.marvelousbob.common.utils.UUID;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
public class RangedPlayerBullet extends Bullet {

    /**
     * rectangle: bottom left + width and height
     */
    private Rectangle rectangle;

    public RangedPlayerBullet(Rectangle rectangle) {
        super(UUID.getNext());
        this.rectangle = rectangle;
    }

    @Override
    public float getCurrCenterX() {
        return rectangle.x + rectangle.width / 2;
    }

    @Override
    public float getCurrCenterY() {
        return rectangle.y + rectangle.height / 2;
    }

    @Override
    public void setCurrCenterX(float x) {
        rectangle.setCenter(x, getCurrCenterX());
    }

    @Override
    public void setCurrCenterY(float y) {
        rectangle.setCenter(getCurrCenterX(), y);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        // no set colors, because drawMe is called by the player instance that shot that bullet.
        // The bullet will be the players color.
        shapeDrawer.filledRectangle(rectangle);
    }
}
