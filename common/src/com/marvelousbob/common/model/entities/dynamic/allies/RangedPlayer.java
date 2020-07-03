package com.marvelousbob.common.model.entities.dynamic.allies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.marvelousbob.common.model.entities.dynamic.projectiles.RangedPlayerBullet;
import com.marvelousbob.common.model.entities.level.Level;
import com.marvelousbob.common.model.entities.level.Wall;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import java.util.Collection;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
@Slf4j
public class RangedPlayer extends Player {

    // TODO: 2020-07-03 fetch dynamically from config, or player stat or something...
    private static final float DEFAULT_INITIAL_BULLET_SPEED = 200f;
    private static final float DEFAULT_INITIAL_BULLET_RADIUS = 3f;


    private Array<RangedPlayerBullet> bullets;
    private float bulletSpeed;

    private final Polygon gun = new Polygon(GUN_VERTICES);
    private static final float GUN_LENGTH = 8;
    private static final float GUN_THICKNESS = 3;
    private static final float[] GUN_VERTICES = new float[]{
            -GUN_LENGTH / 2, -GUN_THICKNESS / 2,
            -GUN_LENGTH / 2, GUN_THICKNESS / 2,
            GUN_LENGTH / 2, GUN_THICKNESS / 2,
            GUN_LENGTH / 2, -GUN_THICKNESS / 2
    };


    public RangedPlayer(UUID uuid, Color color, Vector2 initCenterPos) {
        super(100, 100, 0, color, 40, 20, initCenterPos, uuid);
        this.bulletSpeed = DEFAULT_INITIAL_BULLET_SPEED;
        this.bullets = new Array<>();
    }

    @Override
    public void attack(Vector2 pos) {
        bullets.add(new RangedPlayerBullet(currCenterPos, pos, DEFAULT_INITIAL_BULLET_SPEED,
                DEFAULT_INITIAL_BULLET_RADIUS));
    }

    @Override
    public void updateProjectiles(float delta, Level level) {
        checkForCollisionWithWalls(level.getWalls());
        bullets.forEach(b -> b.updatePos(delta));
        // TODO: 2020-07-03 remove me: this is a dummy technique to remove projectile
        //       while collision detection is not yet implemented    --- OLA
        for (int i = bullets.size - 1; i >= 0; i--) {
            float ARBITRARY_DISTANCE = 400f;
            var bullet = bullets.get(i);
            if (bullet.getCurrentPos().dst(bullet.getStartPos()) > ARBITRARY_DISTANCE) {
                bullets.removeIndex(i);
            }
        }
    }


    // TODO: 2020-07-03 fix wall skip   --- OLA
    public void checkForCollisionWithWalls(Collection<Wall> walls) {
        outer:
        for (int i = bullets.size - 1; i >= 0; i--) {
            var bullet = bullets.get(i);
            for (var wall : walls) {
                if (wall.getRectangle().contains(bullet.getCircle())) {
                    bullets.removeIndex(i);
                    continue outer;
                }
            }
        }
    }

    @Override
    public void updateFromDto(PlayerDto input) {
        super.updateFromDto(input);
//        this.bullets = input.getBullets().stream().map(b -> new TrianglePlayerBullet()) todo
    }

    private void repositionGun() {
        gun.rotate(90);
        gun.setPosition(getCurrCenterX() + getHalfSize(), getCurrCenterY());
        gun.setOrigin(-getHalfSize(), 0);
        gun.setRotation(mouseAngleRelativeToCenter);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(this.color);
        repositionGun();
        shapeDrawer.filledPolygon(gun);
        shapeDrawer.circle(getCurrCenterX(), getCurrCenterY(), getSize() / 2);
        bullets.forEach(b -> b.drawMe(shapeDrawer));
    }

    @Override
    public Polygon getShape() {
        // for now we'll do the square bounding box
        float radius = size / 2;
        return new Polygon(new float[]{
                // top left
                currCenterPos.x + radius, currCenterPos.y + radius,
                // top right
                currCenterPos.x - radius, currCenterPos.y + radius,
                // bottom right
                currCenterPos.x - radius, currCenterPos.y - radius,
                // bottom left
                currCenterPos.x + radius, currCenterPos.y - radius
        });
    }
}
