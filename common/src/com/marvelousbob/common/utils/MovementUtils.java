package com.marvelousbob.common.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
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
     * @param players collection of players to interpolate
     * @param delta   amount of time to be used for the interpolation.
     * @return {@code true} only if at least one movement occurred.
     */
    public static boolean interpolatePlayers(Collection<Player> players, float delta) {
        boolean hasMoved = false;
        for (Player p : players) {
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
    public static boolean interpolatePlayer(Player p, float delta) {
        Vector2 oldPos = p.getCurrCenterPos().cpy();
        if (wantsToMove(p)) {
            log.info("Interpolating player UUID %d with delta %f from (%f , %f) toward (%f , %f)"
                    .formatted(p.getUuid().getId(), delta,
                            p.getCurrCenterX(), p.getCurrCenterY(),
                            p.getDestX(), p.getDestY()));

            p.setCurrCenterX(
                    isBigEnoughDelta(p.getCurrCenterX(), p.getDestX())
                            ? Interpolation.exp10Out.apply(p.getCurrCenterX(), p.getDestX(), delta)
                            : p.getDestX());
            p.setCurrCenterY(
                    isBigEnoughDelta(p.getCurrCenterY(), p.getDestY())
                            ? Interpolation.exp10Out.apply(p.getCurrCenterY(), p.getDestY(), delta)
                            : p.getDestY());

            p.setPreviousPosition(oldPos);
            return true;
        }
        return false;
    }

    private static boolean isBigEnoughDelta(float curr, float dest) {
        return Math.abs(curr - dest) > SMALLEST_DELTA;
    }

    private static boolean wantsToMove(Player p) {
        return p.getCurrCenterX() != p.getDestX()
                || p.getCurrCenterY() != p.getDestY();
    }
}
