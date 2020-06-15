package com.marvelousbob.common.utils;

import com.badlogic.gdx.math.Interpolation;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;

public class MovementUtils {

    private MovementUtils() {
    }

    public static void interpolatePlayers(GameStateDto game, float delta) {
        game.getPlayerDtos().forEach(p -> interpolatePlayer(p, delta));
    }

    public static void interpolatePlayer(PlayerDto p, float delta) {
        // https://github.com/libgdx/libgdx/wiki/Interpolation
        p.setCurrX(Interpolation.exp10Out.apply(p.getCurrX(), p.getDestX(), delta));
        p.setCurrY(Interpolation.exp10Out.apply(p.getCurrY(), p.getDestY(), delta));
    }
}
