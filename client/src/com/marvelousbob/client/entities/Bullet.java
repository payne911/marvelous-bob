package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;

public abstract class Bullet implements Drawable, Identifiable {

   protected Vector2 position;
   protected Vector2 direction;
   protected float speed, size;
}
