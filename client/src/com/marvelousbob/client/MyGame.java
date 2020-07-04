package com.marvelousbob.client;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.network.MyClient;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Every single class in here needs to be initialized within {@link MarvelousBob#create()} in order
 * for their access throughout the program to be safe.
 */
public final class MyGame {

    private MyGame() {
    }

    public static final String GAME_TITLE = "Marvelous Bob";
    public static long LEVEL_SEED = 0;

    /* Player. */
    public static MyClient client;
    public static Controller controller;

    /* Display. */
    public static ShapeDrawer shapeDrawer;
    public static SpriteBatch batch;
    public static BitmapFont font;
    public static Skin skin;
    public static Table root;
    public static Stage stage;
}
