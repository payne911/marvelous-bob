package com.marvelousbob.common.model.entities.dynamic.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.Drawable;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class RangedBulletExplosion implements Drawable {

    public static final int DEFAULT_LIFESPAN = 6;
    // TODO: 2020-07-03 fetch dynamically from config, or player stat or something...
    private static final float DEFAULT_SPEED = 80f;
    private static final float DEFAULT_DISSIPATION = 8f;
    private Vector2 center;
    private float currentRadius;
    private Color color;
    @Getter
    private float currentAlpha;

    // explosion parameter
    private float speed;
    private float dissipation;
    @Getter
    private int lifespan;

    public RangedBulletExplosion(Vector2 center, float currentRadius,
            Color color, float currentAlpha, float speed, float dissipation) {
        this.center = center;
        this.currentRadius = currentRadius;
        this.color = color;
        this.currentAlpha = currentAlpha;
        this.speed = speed;
        this.dissipation = dissipation;
        this.lifespan = 0;
    }

    public static RangedBulletExplosion fromBullet(RangedPlayerBullet bullet) {
        return new RangedBulletExplosion(
                bullet.currentPos.cpy(),
                bullet.size,
                bullet.getColor().cpy(),
                1f,
                DEFAULT_SPEED,
                DEFAULT_DISSIPATION
        );
    }

    public void update(float delta) {
        lifespan++;
        currentRadius += (speed * delta);
        currentAlpha -= (dissipation * delta);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        this.color.a = currentAlpha;
        shapeDrawer.filledCircle(center, currentRadius, color);
    }
}
