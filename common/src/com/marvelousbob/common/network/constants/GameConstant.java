package com.marvelousbob.common.network.constants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class GameConstant {

    public static final int sizeX = 800;
    public static final int sizeY = 600;

    public static final short MAX_PLAYER_AMOUNT = 8;

    public static final Array<Color> playerColors = Array
            .with(Color.WHITE, Color.GREEN, Color.RED, Color.MAGENTA,
                    Color.YELLOW, Color.BROWN, Color.ORANGE, Color.TEAL);
}
