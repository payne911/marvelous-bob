package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.MeleePlayer;
import com.marvelousbob.common.model.entities.Player;
import com.marvelousbob.common.model.entities.RangedPlayer;
import com.marvelousbob.common.utils.UUID;

public enum PlayerTypeDto {
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
