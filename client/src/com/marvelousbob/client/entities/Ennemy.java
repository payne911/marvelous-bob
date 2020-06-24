package com.marvelousbob.client.entities;

import lombok.Getter;
import lombok.Setter;


public abstract class Ennemy implements Drawable {
    @Getter
    @Setter
    protected float health;

    public void dealDamage(float damage) {
        this.health -= damage;
    }

}
