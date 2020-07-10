package com.marvelousbob.common.model.entities.dynamic.enemies;

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

    @EqualsAndHashCode.Include
    protected UUID uuid;

    protected UUID spawnPointUuid;

    protected MovementStrategy movementStrategy;

    public Enemy(UUID uuid, UUID spawnPointUuid, MovementStrategy movementStrategy) {
        this.uuid = uuid;
        this.movementStrategy = movementStrategy;
        this.spawnPointUuid = spawnPointUuid;
        this.hp = maxHp;
    }

    public void dealDamage(float damage) {
        this.hp -= damage;
    }
}
