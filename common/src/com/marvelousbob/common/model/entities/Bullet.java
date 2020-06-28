package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Bullet implements Drawable, Identifiable {

   public Bullet(UUID uuid) {
      this.uuid = uuid;
   }

   protected Vector2 position;
   protected float angle, speed, size;
   protected UUID uuid;
}
