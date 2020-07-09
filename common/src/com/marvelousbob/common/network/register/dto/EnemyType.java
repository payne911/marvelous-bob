package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import com.marvelousbob.common.model.entities.dynamic.enemies.PolygonEnemy;
import com.marvelousbob.common.utils.UUID;

public enum EnemyType {
    POLYGON {
        public Enemy getEnemyType(UUID uuid, UUID spawnPoint, Vector2 initPos, Color color) {
            return new PolygonEnemy(uuid, spawnPoint, initPos, color);
        }
    }/*,
    SPIKED_CIRCLE {
        public Enemy getPlayerInstance(UUID uuid, Color color, Vector2 initPos) {
            return new RangedPlayer(uuid, color, initPos);
        }
    },
    MISSILE {
        public Enemy getPlayerInstance(UUID uuid, Color color, Vector2 initPos) {
            return new RangedPlayer(uuid, color, initPos);
        }
    },
    RANGED {
        public Enemy getPlayerInstance(UUID uuid, Color color, Vector2 initPos) {
            return new RangedPlayer(uuid, color, initPos);
        }
    }*/;

    public abstract Enemy getEnemyType(UUID uuid, UUID spawnPoint, Vector2 initPos, Color color);
}