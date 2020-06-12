package com.marvelousbob.client;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.network.MyClient;
import com.marvelousbob.common.register.Player;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class MyGame {

    /* Client. */
    public static MyClient client;
    public static Player selfPlayer;
    public static Controller controller;

    /* Display. */
    public static ShapeDrawer shapeDrawer;
    public static SpriteBatch batch;
    public static BitmapFont font;
    public static Skin skin;
    public static Table root;
    public static Stage stage;
}
