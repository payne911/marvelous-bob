package com.marvelousbob.client;

import static com.marvelousbob.client.MyGame.GAME_TITLE;
import static com.marvelousbob.client.MyGame.LEVEL_SEED;
import static com.marvelousbob.client.MyGame.batch;
import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.font;
import static com.marvelousbob.client.MyGame.root;
import static com.marvelousbob.client.MyGame.shapeDrawer;
import static com.marvelousbob.client.MyGame.skin;
import static com.marvelousbob.client.MyGame.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.marvelousbob.client.network.MyClient;
import com.marvelousbob.client.screens.GameScreen;
import com.marvelousbob.client.splashScreen.ISplashWorker;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.PlayerConnectionDto;
import com.marvelousbob.common.network.register.dto.PlayerType;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;


/**
 * Takes care of setting up the most basic requirements of the game. Among other things: {@link
 * MyGame} which contains static links to "Singletons".
 */
@Slf4j
public class MarvelousBob extends Game {

    public static final boolean IS_LOCAL = Boolean.parseBoolean(System.getenv("mbs_isLocal"));

    /* Splash Screen. */
    @Setter
    private ISplashWorker splashWorker;

    @Getter
    private GameScreen gameScreen;


    /**
     * Very first method called in the whole game. All the static variables of {@link MyGame} are to
     * be initialized here. The calling order is most probably very sensible.
     */
    @Override
    public void create() {
        removeSplashScreen();
        createClient();

        initializeDisplayElements();
        instantiatePlayer();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling
                        ? GL20.GL_COVERAGE_BUFFER_BIT_NV
                        : 0)); // clear the screen
        Gdx.graphics.setTitle("%s -:- %d FPS   |   local = %b   |   seed = %d".formatted(
                GAME_TITLE,
                Gdx.graphics.getFramesPerSecond(),
                IS_LOCAL,
                LEVEL_SEED));

        // todo: loading screen with AssetManager, THEN `changeScreen()`
        super.render(); // calls the GameScreen's `render()` if the Screen is set (see GameInitializerListener)
    }

    @Override
    public void resize(int width, int height) {
        // this changes viewed screen size, rather than stretch the view
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        font.dispose();
        stage.dispose();

        if (Objects.nonNull(getScreen())) {
            getScreen().dispose();
        }

        /*
        This actually hangs the thread when closing the application...
        See: https://github.com/EsotericSoftware/kryonet/issues/142
        */
//        try {
//            client.getClient().dispose();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // ============================ INIT ==================================


    private void removeSplashScreen() {
        splashWorker.closeSplashScreen();
    }

    private void createClient() {
//        Log.set(LEVEL_TRACE);
        log.warn("---> isLocal? " + IS_LOCAL);
        client = new MyClient(IS_LOCAL, this);
        client.connect();
    }

    private void instantiatePlayer() {
        // todo: add screen for Player Type choice
//        var playerType = MathUtils.random(1f) > .5f
//                ? PlayerTypeDto.RANGED
//                : PlayerTypeDto.MELEE;
        var playerType = PlayerType.RANGED;
        client.getClient().sendTCP(new PlayerConnectionDto(playerType));
//        client.getClient().sendTCP(new PlayerConnectionDto(PlayerTypeDto.MELEE));
//        client.getClient().sendTCP(new PlayerConnectionDto(PlayerTypeDto.RANGED));
    }

    private void initializeDisplayElements() {
        /* https://github.com/raeleus/skin-composer/wiki/From-the-Ground-Up-00:-Scene2D-Primer */
        batch = new SpriteBatch();
//        stage = new Stage(new ExtendViewport(GameConstant.SIZE_X, GameConstant.SIZE_Y), batch);
        stage = new Stage(new FitViewport(GameConstant.SIZE_X, GameConstant.SIZE_Y), batch);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        shapeDrawer = new ShapeDrawer(stage.getBatch(), skin.getRegion("white"));
        font = new BitmapFont();
        root = new Table(skin);
        root.setFillParent(true);
        stage.addActor(root);

//        /* Networking debugging UI component. */
//        var networkDebugUi = new PingTestComponent(root);
//        networkDebugUi.displayNetworkDebuggingUi();
    }


    public void setScreen(GameScreen screen) {
        super.setScreen(screen);
        this.gameScreen = screen;
    }
}
