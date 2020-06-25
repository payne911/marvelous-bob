package com.marvelousbob.client.entities;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


public abstract class Enemy implements Drawable, Identifiable {
    @Getter
    @Setter
    protected float health;

    @Getter
    protected UUID id;

    public void dealDamage(float damage) {
        this.health -= damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return Objects.equals(id, enemy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
