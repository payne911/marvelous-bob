package com.marvelousbob.common.utils;

import com.badlogic.gdx.math.Interpolation;
import com.marvelousbob.common.network.register.dto.GameStateDto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovementUtils {

    /**
     * Optimisation to prevent interpolation from trying to calculate unnoticeable differences.
     * <p>
     * Increasing this value will increase the performance by reducing calculations.
     */
    private static final float SMALLEST_DELTA = 1;

    private MovementUtils() {
    }

    /**
     * @param game  the game state which will see all its players be interpolated to their desired
     *              destination.
     * @param delta amount of time to be used for the interpolation.
     * @return {@code true} only if at least one movement occurred.
     */
    public static boolean interpolatePlayers(GameStateDto game, float delta) {
        return interpolatePlayers(game.getPlayersDtos().values(), delta);
    }

    /**
     * @param players collection of players to interpolate
     * @param delta   amount of time to be used for the interpolation.
     * @return {@code true} only if at least one movement occurred.
     */
    public static boolean interpolatePlayers(Collection<PlayerDto> players, float delta) {
        boolean hasMoved = false;
        for (PlayerDto p : players) {
            hasMoved |= MovementUtils.interpolatePlayer(p, delta);
        }
        return hasMoved;
    }

    /**
     * @param p     a player to interpolate
     * @param delta the amount of time which has past since last render
     * @return {@code true} only if a movement occurred.
     * @see <a href="https://github.com/libgdx/libgdx/wiki/Interpolation">libGDX Interpolation Wiki
     * Page</a>
     */
    public static boolean interpolatePlayer(PlayerDto p, float delta) {
        if (wantsToMove(p)) {
            log.info("Interpolating player UUID %d with delta %f from (%f , %f) toward (%f , %f)"
                    .formatted(p.getUuid().getId(), delta,
                            p.getCurrX(), p.getCurrY(), p.getDestX(), p.getDestY()));

            p.setCurrX(isBigEnoughDelta(p.getCurrX(), p.getDestX())
                    ? Interpolation.exp10Out.apply(p.getCurrX(), p.getDestX(), delta)
                    : p.getDestX());
            p.setCurrY(isBigEnoughDelta(p.getCurrY(), p.getDestY())
                    ? Interpolation.exp10Out.apply(p.getCurrY(), p.getDestY(), delta)
                    : p.getDestY());

            return true;
        }
        return false;
    }

    private static boolean isBigEnoughDelta(float curr, float dest) {
        return Math.abs(curr - dest) > SMALLEST_DELTA;
    }

    private static boolean wantsToMove(PlayerDto p) {
        return p.getCurrX() != p.getDestX() || p.getCurrY() != p.getDestY();
    }
}
