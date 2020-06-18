package com.marvelousbob.common.utils;

import com.badlogic.gdx.math.Interpolation;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovementUtils {

    /**
     * Optimisation to prevent interpolation from trying to calculate unnoticeable differences.
     */
    private static final float SMALLEST_DELTA = 0.5f;

    private MovementUtils() {
    }

    public static void interpolatePlayers(GameStateDto game, float delta) {
        game.getPlayerDtos().forEach(p -> interpolatePlayer(p, delta));
    }

    /**
     * @param p     a player to interpolate
     * @param delta the amount of time which has past since last render
     * @see <a href="https://github.com/libgdx/libgdx/wiki/Interpolation">libGDX Interpolation Wiki
     * Page</a>
     */
    public static void interpolatePlayer(PlayerDto p, float delta) {

        if (wantsToMove(p)) {
            log.info("Interpolating player UUID %s from (%f , %f) to (%f , %f)"
                    .formatted(p.getUuid().getStringId(), p.getCurrX(), p.getCurrY(), p.getDestX(),
                            p.getDestY()));

            p.setCurrX(isBigEnoughDelta(p.getCurrX(), p.getDestX())
                    ? Interpolation.exp10Out.apply(p.getCurrX(), p.getDestX(), delta)
                    : p.getDestX());
            p.setCurrY(isBigEnoughDelta(p.getCurrY(), p.getDestY())
                    ? Interpolation.exp10Out.apply(p.getCurrY(), p.getDestY(), delta)
                    : p.getDestY());
        }
    }

    private static boolean isBigEnoughDelta(float currX, float destX) {
        return Math.abs(currX - destX) > SMALLEST_DELTA;
    }

    private static boolean wantsToMove(PlayerDto p) {
        return p.getCurrX() != p.getDestX() || p.getCurrY() != p.getDestY();
    }
}
