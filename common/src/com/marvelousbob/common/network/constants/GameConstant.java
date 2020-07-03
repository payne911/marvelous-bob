package com.marvelousbob.common.network.constants;

/**
 * NOTE: nothing random in here can be expected to be identical between the Client and the Server.
 */
public class GameConstant {

    public static final int WRITE_BUFFER_SIZE = 65536;
    public static final int OBJECT_BUFFER_SIZE = 8192;

    public static final int SIZE_X = 1024;
    public static final int SIZE_Y = 768;

    public static final int PIXELS_PER_GRID_CELL = 30; // todo: player default size according to this?

    public static final int BLOCKS_X = SIZE_X / PIXELS_PER_GRID_CELL;
    public static final int BLOCKS_Y = SIZE_Y / PIXELS_PER_GRID_CELL;
}
