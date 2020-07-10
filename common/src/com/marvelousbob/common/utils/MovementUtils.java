package com.marvelousbob.common.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
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

    private static Interpolation interpolation = Interpolation.linear;

    private MovementUtils() {
    }


    /**
     * Updates the pos vector passed in moving at a constant speed in the direction of the angle
     * provided
     *
     * @param pos   the initaial position
     * @param speed the speed at which to move
     * @param angle the angle (in radians) of the movement.
     */
    public static void updatePosConstantSpeed(Vector2 pos, float speed, float angle) {
        pos.x += speed * MathUtils.cos(angle);
        pos.y += speed * MathUtils.sin(angle);
    }


    /**
     * Calculates a new position which is the
     *
     * @param pos
     * @param target
     * @param speed
     * @return
     */
    public static Vector2 constantSpeed(Vector2 pos, Vector2 target, float speed) {
        float angle = MathUtils.atan2(target.y - pos.y, target.x - pos.x);
        return constantSpeed(pos, speed, angle);
    }


    public static Vector2 constantSpeed(Vector2 pos, float speed, float angle) {
        float x = pos.x + speed * MathUtils.cos(angle);
        float y = pos.y + speed * MathUtils.sin(angle);
        return new Vector2(x, y);
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
//            log.info("Interpolating player UUID %d with delta %f from (%f , %f) toward (%f , %f)"
//                    .formatted(p.getUuid().getId(), delta,
//                            p.getCurrCenterX(), p.getCurrCenterY(),
//                            p.getDestX(), p.getDestY()));

            p.setCurrCenterX(
                    isBigEnoughDelta(p.getCurrCenterX(), p.getDestX())
                            ? interpolation.apply(p.getCurrCenterX(), p.getDestX(), delta)
                            : p.getDestX());
            p.setCurrCenterY(
                    isBigEnoughDelta(p.getCurrCenterY(), p.getDestY())
                            ? interpolation.apply(p.getCurrCenterY(), p.getDestY(), delta)
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

    public static void moveEnemy(Enemy enemy, float delta) {
        Vector2 newPos = enemy.getMovementStrategy().move(
                new Vector2(enemy.getCurrCenterX(), enemy.getCurrCenterY()),
                delta * 50f);
        enemy.setCurrCenterY(newPos.y);
        enemy.setCurrCenterX(newPos.x);
    }
}
