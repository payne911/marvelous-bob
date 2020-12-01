package com.marvelousbob.common.model.entities.dynamic.allies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.projectiles.RangedBulletExplosion;
import com.marvelousbob.common.model.entities.dynamic.projectiles.RangedPlayerBullet;
import com.marvelousbob.common.model.entities.level.Wall;
import com.marvelousbob.common.state.Level;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class RangedPlayer extends Player<RangedPlayerBullet> {

    // TODO: 2020-07-03 fetch dynamically from config, or player stat or something...
    private static final float DEFAULT_INITIAL_BULLET_SPEED = 200f;
    public static final float ARBITRARY_BULLET_MAX_DISTANCE = 200f;
    private static final float DEFAULT_INITIAL_BULLET_RADIUS = 3f;


    @Getter
    private ConcurrentHashMap<UUID, RangedPlayerBullet> bullets;
    private ArrayList<RangedBulletExplosion> explosions;
    @Getter
    private float bulletSpeed;
    @Getter
    private float bulletSize;

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
        this.bulletSize = DEFAULT_INITIAL_BULLET_RADIUS;
        this.bullets = new ConcurrentHashMap<>();
        this.explosions = new ArrayList<>();
    }

    @Override
    public void attack(Vector2 pos) {
        var newBullet = new RangedPlayerBullet(currCenterPos, pos, color, bulletSpeed, bulletSize);
        bullets.put(newBullet.getUuid(), newBullet);
    }

    public Collection<RangedPlayerBullet> getBulletsList() {
        return bullets.values();
    }

    @Override
    public void updateProjectiles(float delta, Level level) {
        checkForCollisionWithWalls(level.getWalls());
        var bulletsList = getBulletsList();
        bulletsList.forEach(b -> b.updatePos(delta));
        explosions.forEach(e -> e.update(delta));
        // TODO: 2020-07-03 remove me: this is a dummy technique to remove projectile
        //       while collision detection is not yet implemented    --- OLA
        bulletsList.forEach(bullet -> {
            if (bullet.getCurrentPos().dst(bullet.getStartPos()) > ARBITRARY_BULLET_MAX_DISTANCE) {
                explodeBullet(bullet);
            }
        });
        if (!explosions.isEmpty()) {
            for (int i = explosions.size() - 1; i >= 0; i--) {
                if (explosions.get(i).getLifespan() >= RangedBulletExplosion.DEFAULT_LIFESPAN) {
                    explosions.remove(i);
                }
            }
        }
    }

    public boolean containsBulletUuid(UUID uuid) {
        return bullets.containsKey(uuid);
    }

    public void removeBullet(UUID uuid) {
        if (!containsBulletUuid(uuid)) {
            log.error("Trying to remove a bullet which isn't in the list.");
            return;
        }

        var iterator = bullets.keySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(uuid)) {
                iterator.remove();
                break;
            }
        }
    }

    public void explodeBullet(RangedPlayerBullet bullet) {
        removeBullet(bullet.getUuid());
        explosions.add(RangedBulletExplosion.fromBullet(bullet));
    }


    // TODO: 2020-07-03 fix wall skip   --- OLA
    public void checkForCollisionWithWalls(Collection<Wall> walls) {
        outer:
        for (var bullet : getBulletsList()) {
            for (var wall : walls) {
                if (wall.getRectangle().contains(bullet.getCircle())) {
                    explodeBullet(bullet);
                    continue outer;
                }
            }
        }
    }

    @Override
    public void addBullet(RangedPlayerBullet bullet) {
        bullets.put(bullet.getUuid(), bullet);
    }

    private void repositionGun(float delta) {
        mouseAngleRelativeToCenter = MathUtils.lerpAngleDeg(
                mouseAngleRelativeToCenter, desiredMouseAngleRelativeToCenter, delta);

        gun.rotate(90);
        gun.setPosition(getCurrCenterX() + getHalfSize(), getCurrCenterY());
        gun.setOrigin(-getHalfSize(), 0);
        gun.setRotation(mouseAngleRelativeToCenter);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(this.color);
        repositionGun(Gdx.graphics.getDeltaTime() * 25);
        shapeDrawer.filledPolygon(gun);
        shapeDrawer.circle(getCurrCenterX(), getCurrCenterY(), getSize() / 2);
        getBulletsList().forEach(b -> b.drawMe(shapeDrawer));
        explosions.forEach(e -> e.drawMe(shapeDrawer));
    }

    @Override
    public Polygon getShape() {
        // for now we'll do the square bounding box
        float radius = size / 2;
        return new Polygon(new float[]{
                currCenterPos.x + radius, currCenterPos.y + radius, // top left
                currCenterPos.x - radius, currCenterPos.y + radius, // top right
                currCenterPos.x - radius, currCenterPos.y - radius, // bottom right
                currCenterPos.x + radius, currCenterPos.y - radius  // bottom left
        });
    }
}
