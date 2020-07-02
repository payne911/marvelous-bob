package com.marvelousbob.server.factories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.level.Wall;

public class WallFactory {

    private static final float THICKNESS = 6;
    private static final float HALF_THICKNESS = THICKNESS / 2;

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    public enum BeginFrom { // todo: rename to "Headed" or "HeadToward" ?
        LEFT, RIGHT, TOP, BOTTOM
    }

    /**
     * Calls {@link #buildWall(Orientation, BeginFrom, Vector2, float, Color)} with a default color
     * of {@code BLACK}.
     */
    public Wall buildWall(Orientation orientation, BeginFrom beginFrom, Vector2 pos, float length) {
        return buildWall(orientation, beginFrom, pos, length, Color.BLACK);
    }

    /**
     * @param orientation the Horizontal or Vertical orientation of the wall
     * @param beginFrom   where in the orientation does the 'pos' parameter value start
     * @param pos         pair of coordinates for the selected 'begin' position
     * @param length      length along the orientation (the other size will be a default thickness)
     * @param color       the color for the wall
     */
    public Wall buildWall(Orientation orientation, BeginFrom beginFrom, Vector2 pos, float length,
            Color color) {
        enforceValidOrientation(orientation, beginFrom);

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
        switch (beginFrom) {
            case RIGHT -> bottomLeftX -= length;
            case TOP -> bottomLeftY -= length;
        }

        return new Wall(bottomLeftX, bottomLeftY, width, height, color);
    }

    /**
     * A version of {@link #buildWall(Orientation, BeginFrom, Vector2, float, Color)} which will
     * slightly displace the input position so that it is centered relative to the thickness.
     * <p>
     * Basically, depending on the orientation, half the value of the thickness will be deduced from
     * one of the axis of the position.
     */
    public Wall buildCenteredWall(Orientation orientation, BeginFrom beginFrom, Vector2 pos,
            float length, Color color) {
        enforceValidOrientation(orientation, beginFrom);

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
        switch (beginFrom) {
            case RIGHT -> bottomLeftX -= length;
            case TOP -> bottomLeftY -= length;
        }

        return new Wall(bottomLeftX, bottomLeftY, width, height, color);
    }

    /**
     * Calls {@link #buildCenteredWall(Orientation, BeginFrom, Vector2, float, Color)} with a
     * default {@code BLACK} color.
     */
    public Wall buildCenteredWall(Orientation orientation, BeginFrom beginFrom, Vector2 pos,
            float length) {
        return buildCenteredWall(orientation, beginFrom, pos, length, Color.BLACK);
    }

    /**
     * A version of {@link #buildWall(Orientation, BeginFrom, Vector2, float, Color)} which will
     * slightly displace the position and increase the length so that walls of different
     * orientations placed on the same point will have overlapping corners.
     * <p>
     * The actual displacement/enlargement is related to {@link #THICKNESS} (value of {@value
     * #THICKNESS}).
     */
    public Wall buildBlendedWall(Orientation orientation, BeginFrom beginFrom, Vector2 pos,
            float length, Color color) {
        enforceValidOrientation(orientation, beginFrom);

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
        switch (beginFrom) {
            case RIGHT -> bottomLeftX -= length;
            case TOP -> bottomLeftY -= length;
        }

        /* Recenter */
        switch (beginFrom) {
            case LEFT -> bottomLeftX -= HALF_THICKNESS;
            case RIGHT -> bottomLeftX += HALF_THICKNESS;
            case TOP -> bottomLeftY += HALF_THICKNESS;
            case BOTTOM -> bottomLeftY -= HALF_THICKNESS;
        }

        return new Wall(bottomLeftX, bottomLeftY, width, height, color);
    }

    /**
     * Calls {@link #buildBlendedWall(Orientation, BeginFrom, Vector2, float, Color)} with a default
     * {@code BLACK} color.
     */
    public Wall buildBlendedWall(Orientation orientation, BeginFrom beginFrom, Vector2 pos,
            float length) {
        return buildBlendedWall(orientation, beginFrom, pos, length, Color.BLACK);
    }

    /**
     * Calls {@link #buildWall(Orientation, BeginFrom, Vector2, float)} using a default value for
     * the {@code begin} parameter: if the {@code orientation} is {@link Orientation#HORIZONTAL},
     * then it defaults to {@link BeginFrom#LEFT}, otherwise it defaults to {@link
     * BeginFrom#RIGHT}.<p> The default color will be {@code BLACK}.
     */
    public Wall buildWall(Orientation orientation, Vector2 pos, float length) {
        BeginFrom beginFrom = orientation == Orientation.HORIZONTAL
                ? BeginFrom.LEFT
                : BeginFrom.BOTTOM;
        return buildWall(orientation, beginFrom, pos, length);
    }

    /**
     * Calls {@link  #buildWall(Orientation, BeginFrom, Vector2, float, Color)} using a default
     * value for the {@code begin} parameter: if the {@code orientation} is {@link
     * Orientation#HORIZONTAL}, then it defaults to {@link BeginFrom#LEFT}, otherwise it defaults to
     * {@link BeginFrom#RIGHT}.
     */
    public Wall buildWall(Orientation orientation, Vector2 pos, float length, Color color) {
        BeginFrom beginFrom = orientation == Orientation.HORIZONTAL
                ? BeginFrom.LEFT
                : BeginFrom.BOTTOM;
        return buildWall(orientation, beginFrom, pos, length, color);
    }

    private boolean isValidOrientation(Orientation orientation, BeginFrom beginFrom) {
        return (orientation == Orientation.HORIZONTAL
                && (beginFrom == BeginFrom.LEFT || beginFrom == BeginFrom.RIGHT))
                || (orientation == Orientation.VERTICAL
                && (beginFrom == BeginFrom.TOP || beginFrom == BeginFrom.BOTTOM));
    }

    private void enforceValidOrientation(Orientation orientation, BeginFrom beginFrom) {
        if (!isValidOrientation(orientation, beginFrom)) {
            throw new IllegalArgumentException(
                    "This `BEGIN` value only matches the other `ORIENTATION`.");
        }
    }
}
