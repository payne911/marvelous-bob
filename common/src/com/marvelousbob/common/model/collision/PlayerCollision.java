package com.marvelousbob.common.model.collision;

import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.model.entities.level.Wall;
import lombok.Data;

@Data
public class PlayerCollision {

    public final Player player;
    public final Wall wall;

    public void logDebug() {
        System.out.println(
                "Player %s is colliding with wall %s".formatted(player.toString(), wall.toString())
        );
    }
}
