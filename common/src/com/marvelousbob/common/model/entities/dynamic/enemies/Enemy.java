package com.marvelousbob.common.model.entities.dynamic.enemies;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.entities.Drawable;
import com.marvelousbob.common.model.entities.Movable;
import com.marvelousbob.common.utils.UUID;
import com.marvelousbob.common.utils.movements.MovementStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class Enemy implements Drawable, Identifiable, Movable {

    protected float hp, maxHp = 100;
    protected MovementStrategy<Vector2> movementStrategy;

    @EqualsAndHashCode.Include
    protected UUID uuid;

    protected UUID spawnPointUuid;

    public Enemy(UUID uuid, UUID spawnPointUuid, MovementStrategy<Vector2> movementStrategy) {
        this.uuid = uuid;
        this.spawnPointUuid = spawnPointUuid;
        this.hp = maxHp;
        this.movementStrategy = movementStrategy;
    }

    public void move(float delta) {
        Vector2 newPos = movementStrategy
                .move(new Vector2(getCurrCenterX(), getCurrCenterY()), delta * 50f);
        setCurrCenterY(newPos.y);
        setCurrCenterX(newPos.x);
    }

    public void dealDamage(float damage) {
        this.hp -= damage;
    }
}
