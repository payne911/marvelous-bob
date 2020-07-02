package com.marvelousbob.common.network.constants;

public class GameConstant {

    public static final int WRITE_BUFFER_SIZE = 65536;
    public static final int OBJECT_BUFFER_SIZE = 8192;

    public static final int SIZE_X = 800;
    public static final int SIZE_Y = 600;

    public static final int PIXELS_PER_GRID_CELL = 20; // todo: player default size according to this?

    public static final int BLOCKS_X = SIZE_X / PIXELS_PER_GRID_CELL;
    public static final int BLOCKS_Y = SIZE_Y / PIXELS_PER_GRID_CELL;
}
