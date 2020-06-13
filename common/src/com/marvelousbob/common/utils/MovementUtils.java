package com.marvelousbob.common.utils;

import com.badlogic.gdx.math.Interpolation;
import com.marvelousbob.common.network.register.GameState;
import com.marvelousbob.common.network.register.Player;

public class MovementUtils {

    public static void interpolatePlayers(GameState game, float delta) {
        game.getPlayers().forEach(p -> interpolatePlayer(p, delta));
    }

    private static void interpolatePlayer(Player p, float delta) {
        // https://github.com/libgdx/libgdx/wiki/Interpolation
        p.setCurrX(Interpolation.exp10Out.apply(p.getCurrX(), p.getDestX(), delta));
        p.setCurrY(Interpolation.exp10Out.apply(p.getCurrY(), p.getDestY(), delta));
    }
}
