package com.marvelousbob.common.model.entities.dynamic.allies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.projectiles.Bullet;
import com.marvelousbob.common.state.Level;
import com.marvelousbob.common.utils.UUID;
import lombok.NoArgsConstructor;
import lombok.ToString;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
public class MeleePlayer extends Player {

    public static final Color ROD_COLOR = Color.RED.cpy();
    private static final float ROD_SIZE = 3;


    public MeleePlayer(UUID uuid, Color color, Vector2 initCenterPos) {
        super(100, 100, 0, color, 40, 20, initCenterPos, uuid);
    }

    @Override
    public void attack(Vector2 pos) {
        // TODO: 2020-07-03   --- OLA
    }

    @Override
    public void updateProjectiles(float delta, Level level) {
        // TODO: 2020-07-03   --- OLA
    }

    private void drawRod(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(ROD_COLOR); // whatever weapon color

        float centerX, centerY, radiusX, radiusY;
        if (mouseAngleRelativeToCenter >= 45 && mouseAngleRelativeToCenter < 135) { // top
            centerX = getCurrCenterX();
            centerY = getCurrCenterY() + getHalfSize();
            radiusX = getHalfSize();
            radiusY = ROD_SIZE;
        } else if (mouseAngleRelativeToCenter >= 135 && mouseAngleRelativeToCenter < 225) { // left
            centerX = getCurrCenterX() - getHalfSize();
            centerY = getCurrCenterY();
            radiusX = ROD_SIZE;
            radiusY = getHalfSize();
        } else if (mouseAngleRelativeToCenter >= 225
                && mouseAngleRelativeToCenter < 315) { // bottom
            centerX = getCurrCenterX();
            centerY = getCurrCenterY() - getHalfSize();
            radiusX = getHalfSize();
            radiusY = ROD_SIZE;
        } else { // right
            centerX = getCurrCenterX() + getHalfSize();
            centerY = getCurrCenterY();
            radiusX = ROD_SIZE;
            radiusY = getHalfSize();
        }

        shapeDrawer.filledEllipse(centerX, centerY, radiusX, radiusY);
        shapeDrawer.setColor(this.color); // revert back to original
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(this.color);
        shapeDrawer.rectangle(getCurrCenterX() - size / 2, getCurrCenterY() - size / 2, size, size);
        drawRod(shapeDrawer);
    }

    @Override
    public Polygon getShape() {
        float sizeOverTwo = size / 2;
        return new Polygon(new float[]{
                // top left
                currCenterPos.x + sizeOverTwo, currCenterPos.y + sizeOverTwo,
                // top right
                currCenterPos.x - sizeOverTwo, currCenterPos.y + sizeOverTwo,
                // bottom right
                currCenterPos.x - sizeOverTwo, currCenterPos.y - sizeOverTwo,
                // bottom left
                currCenterPos.x + sizeOverTwo, currCenterPos.y - sizeOverTwo

        });
    }

    // TODO: 2020-07-03 Melee payer 'Bullets' ???    --- OLA
    @Override
    public void addBullet(Bullet bullet) {

    }
}
