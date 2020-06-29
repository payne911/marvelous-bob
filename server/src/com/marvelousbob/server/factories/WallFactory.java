package com.marvelousbob.server.factories;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.entities.level.Wall;

public class WallFactory {

    private static final float THICKNESS = 8;

    public enum ORIENTATION {
        HORIZONTAL, VERTICAL
    }

    public enum BEGIN {
        LEFT, RIGHT, TOP, BOTTOM
    }

    /**
     * @param orientation the Horizontal or Vertical orientation of the wall
     * @param begin       where in the orientation does the 'pos' parameter value start
     * @param pos         pair of coordinates for the selected 'begin' position
     * @param length      length along the orientation (the other size will be a default thickness)
     */
    public Wall buildWall(ORIENTATION orientation, BEGIN begin, Vector2 pos, float length) {
        if (!isValidOrientation(orientation, begin)) {
            throw new IllegalArgumentException(
                    "This `BEGIN` value only matches the other `ORIENTATION`.");
        }

        float bottomLeft_X = pos.x;
        float bottomLeft_Y = pos.y;
        float width = THICKNESS;
        float height = THICKNESS;

        switch (orientation) {
            case HORIZONTAL -> width = length;
            case VERTICAL -> height = length;
        }

        switch (begin) {
            case RIGHT -> bottomLeft_X -= length;
            case TOP -> bottomLeft_Y -= length;
        }

        return new Wall(bottomLeft_X, bottomLeft_Y, width, height);
    }

    /**
     * Calls {@link #buildWall(ORIENTATION, BEGIN, Vector2, float)} using a default value for the
     * {@code begin} parameter: if the {@code orientation} is {@link ORIENTATION#HORIZONTAL}, then
     * it defaults to {@link BEGIN#LEFT}, otherwise it defaults to {@link BEGIN#RIGHT}.
     */
    public Wall buildWall(ORIENTATION orientation, Vector2 pos, float length) {
        BEGIN begin = orientation == ORIENTATION.HORIZONTAL
                ? BEGIN.LEFT
                : BEGIN.BOTTOM;
        return buildWall(orientation, begin, pos, length);
    }

    private boolean isValidOrientation(ORIENTATION orientation, BEGIN begin) {
        return (orientation == ORIENTATION.HORIZONTAL
                && (begin == BEGIN.LEFT || begin == BEGIN.RIGHT))
                || (orientation == ORIENTATION.VERTICAL
                && (begin == BEGIN.TOP || begin == BEGIN.BOTTOM));
    }
}
