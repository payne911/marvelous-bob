package com.marvelousbob.common.model.entities;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Enemy implements Drawable, Identifiable {
    @Getter
    @Setter
    protected float health;

    @Getter
    @Include
    protected UUID uuid;

    public void dealDamage(float damage) {
        this.health -= damage;
    }
}
