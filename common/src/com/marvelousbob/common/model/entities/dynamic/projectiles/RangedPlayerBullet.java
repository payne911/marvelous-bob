package com.marvelousbob.common.model.entities.dynamic.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.utils.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;

@NoArgsConstructor
@ToString(callSuper = true)
@Slf4j
public class RangedPlayerBullet extends Bullet {

    /**
     * rectangle: bottom left + width and height
     */
    @ToString.Exclude
    @Getter
    private Circle circle;
    @Getter
    private Vector2 startPos;

    @Getter
    private Color color;

    public RangedPlayerBullet(Vector2 initPos, Vector2 dest, Color color, float speed,
            float radius) {
        super(UUID.getNext());
        this.startPos = initPos;
        this.currentPos = initPos;
        this.speed = speed;
        this.angle = MathUtils.atan2(dest.y - initPos.y, dest.x - initPos.x);
        this.size = radius;
        this.circle = new Circle(initPos.x, initPos.y, radius);
        this.color = color;
    }

    @Override
    public void updatePos(float delta) {
        float x = currentPos.x + speed * delta * MathUtils.cos(angle);
        float y = currentPos.y + speed * delta * MathUtils.sin(angle);
        this.currentPos = new Vector2(x, y);
        circle.setPosition(x, y);
    }

    @Override
    public float getCurrCenterX() {
        return currentPos.x;
    }

    @Override
    public void setCurrCenterX(float x) {
        circle.setX(x);
    }

    @Override
    public float getCurrCenterY() {
        return currentPos.y;
    }

    @Override
    public void setCurrCenterY(float y) {
        circle.setY(y);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(color);
        shapeDrawer.filledCircle(circle.x, circle.y, circle.radius);
    }
}
