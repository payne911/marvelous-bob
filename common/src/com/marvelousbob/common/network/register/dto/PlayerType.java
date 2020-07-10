package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.allies.MeleePlayer;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.model.entities.dynamic.allies.RangedPlayer;
import com.marvelousbob.common.utils.UUID;

public enum PlayerType {
    RANGED {
        public Player getPlayerInstance(UUID uuid, Color color, Vector2 initPos) {
            return new RangedPlayer(uuid, color, initPos);
        }
    },

    MELEE {
        public Player getPlayerInstance(UUID uuid, Color color, Vector2 initPos) {
            return new MeleePlayer(uuid, color, initPos);
        }

    };

    public abstract Player getPlayerInstance(UUID uuid, Color color, Vector2 initPos);

}
