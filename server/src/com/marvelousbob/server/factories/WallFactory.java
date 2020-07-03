package com.marvelousbob.server.factories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.level.Wall;

public class WallFactory {

    private static final float THICKNESS = 12;
    private static final float HALF_THICKNESS = THICKNESS / 2;

    /**
     * {@link #HORIZONTAL} will use {@link Headed#RIGHT} by default.
     * <p>
     * {@link #VERTICAL} will use {@link Headed#TOP} by default.
     *
     * @see #getDefaultHeaded(Orientation)
     */
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    public enum Headed {
        LEFT, RIGHT, TOP, BOTTOM
    }

    /**
     * @param orientation the Horizontal or Vertical orientation of the wall
     * @param headed      in which direction along the orientation is the wall headed
     * @param pos         pair of coordinates for the selected 'begin' position
     * @param length      length along the orientation (the other size will be a default thickness)
     * @param color       the color for the wall
     */
    public Wall buildWall(Orientation orientation, Headed headed, Vector2 pos, float length,
            Color color) {
        validate(orientation, headed, length);

        /* Size: default + adjustment. */
        float width = THICKNESS;
        float height = THICKNESS;
        switch (orientation) {
            case HORIZONTAL -> width = length;
            case VERTICAL -> height = length;
        }

        /* Position: default + adjustment. */
        float bottomLeftX = pos.x;
        float bottomLeftY = pos.y;
        switch (headed) {
            case LEFT -> bottomLeftX -= length;
            case BOTTOM -> bottomLeftY -= length;
        }

        return new Wall(bottomLeftX, bottomLeftY, width, height, color);
    }

    /**
     * A version of {@link #buildWall(Orientation, Headed, Vector2, float, Color)} which will
     * slightly displace the input position so that it is centered relative to the thickness.
     * <p>
     * Basically, depending on the orientation, half the value of the thickness will be deduced from
     * one of the axis of the position.
     */
    public Wall buildCenteredWall(Orientation orientation, Headed headed, Vector2 pos,
            float length, Color color) {
        validate(orientation, headed, length);

        float width = THICKNESS;
        float height = THICKNESS;
        float bottomLeftX = pos.x;
        float bottomLeftY = pos.y;

        /* Determining which side is thin  &  Center on point */
        switch (orientation) {
            case HORIZONTAL -> {
                width = length;
                bottomLeftY -= HALF_THICKNESS;
            }
            case VERTICAL -> {
                height = length;
                bottomLeftX -= HALF_THICKNESS;
            }
        }

        /* Adjusting alignment */
        switch (headed) {
            case LEFT -> bottomLeftX -= length;
            case BOTTOM -> bottomLeftY -= length;
        }

        return new Wall(bottomLeftX, bottomLeftY, width, height, color);
    }

    /**
     * A version of {@link #buildWall(Orientation, Headed, Vector2, float, Color)} which will
     * slightly displace the position and increase the length so that walls of different
     * orientations placed on the same point will have overlapping corners.
     * <p>
     * The actual displacement/enlargement is related to {@link #THICKNESS} (value of {@value
     * #THICKNESS}).
     */
    public Wall buildBlendedWall(Orientation orientation, Headed headed, Vector2 pos,
            float length, Color color) {
        validate(orientation, headed, length);

        length += THICKNESS; // enlarge
        float width = THICKNESS;
        float height = THICKNESS;
        float bottomLeftX = pos.x;
        float bottomLeftY = pos.y;

        /* Determining which side is thin  &  Center on point */
        switch (orientation) {
            case HORIZONTAL -> {
                width = length;
                bottomLeftY -= HALF_THICKNESS;
            }
            case VERTICAL -> {
                height = length;
                bottomLeftX -= HALF_THICKNESS;
            }
        }

        /* Adjusting alignment */
        switch (headed) {
            case LEFT -> bottomLeftX -= length;
            case BOTTOM -> bottomLeftY -= length;
        }

        /* Recenter */
        switch (headed) {
            case RIGHT -> bottomLeftX -= HALF_THICKNESS;
            case LEFT -> bottomLeftX += HALF_THICKNESS;
            case BOTTOM -> bottomLeftY += HALF_THICKNESS;
            case TOP -> bottomLeftY -= HALF_THICKNESS;
        }

        return new Wall(bottomLeftX, bottomLeftY, width, height, color);
    }

    // =========================================
    // VALIDATION & UTILS

    private boolean isValidOrientation(Orientation orientation, Headed headed) {
        return (orientation == Orientation.HORIZONTAL
                && (headed == Headed.LEFT || headed == Headed.RIGHT))
                || (orientation == Orientation.VERTICAL
                && (headed == Headed.TOP || headed == Headed.BOTTOM));
    }

    private void validate(Orientation orientation, Headed headed, float length) {
        if (!isValidOrientation(orientation, headed)) {
            throw new IllegalArgumentException(
                    "This `BEGIN` value only matches the other `ORIENTATION`.");
        }

        if (length < 0) {
            throw new IllegalArgumentException("Cannot have a negative length.");
        }
    }

    private Headed getDefaultHeaded(Orientation orientation) {
        return orientation == Orientation.HORIZONTAL
                ? Headed.RIGHT
                : Headed.TOP;
    }

    // =========================================
    // OVERLOADED methods

    /**
     * @see #buildBlendedWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildBlendedWall(Orientation orientation, Headed headed, Vector2 pos,
            float length) {
        return buildBlendedWall(orientation, headed, pos, length, Wall.DEFAULT_COLOR);
    }

    /**
     * @see #buildBlendedWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildBlendedWall(Orientation orientation, Vector2 pos, float length, Color color) {
        return buildBlendedWall(orientation, getDefaultHeaded(orientation), pos, length, color);
    }

    /**
     * @see #buildBlendedWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildBlendedWall(Orientation orientation, Vector2 pos, float length) {
        return buildBlendedWall(orientation, getDefaultHeaded(orientation), pos, length,
                Wall.DEFAULT_COLOR);
    }

    /**
     * @see #buildCenteredWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildCenteredWall(Orientation orientation, Vector2 pos, float length) {
        return buildCenteredWall(orientation, getDefaultHeaded(orientation), pos, length,
                Wall.DEFAULT_COLOR);
    }

    /**
     * @see #buildCenteredWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildCenteredWall(Orientation orientation, Vector2 pos, float length, Color color) {
        return buildCenteredWall(orientation, getDefaultHeaded(orientation), pos, length,
                color);
    }

    /**
     * @see #buildCenteredWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildCenteredWall(Orientation orientation, Headed headed, Vector2 pos,
            float length) {
        return buildCenteredWall(orientation, headed, pos, length, Wall.DEFAULT_COLOR);
    }

    /**
     * @see #buildWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildWall(Orientation orientation, Vector2 pos, float length) {
        return buildWall(orientation, getDefaultHeaded(orientation), pos, length,
                Wall.DEFAULT_COLOR);
    }

    /**
     * @see #buildWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildWall(Orientation orientation, Vector2 pos, float length, Color color) {
        return buildWall(orientation, getDefaultHeaded(orientation), pos, length, color);
    }

    /**
     * @see #buildWall(Orientation, Headed, Vector2, float, Color)
     */
    public Wall buildWall(Orientation orientation, Headed headed, Vector2 pos, float length) {
        return buildWall(orientation, headed, pos, length, Wall.DEFAULT_COLOR);
    }
}
