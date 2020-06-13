package com.marvelousbob.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.client.inputProcessors.MyGestureListener;
import com.marvelousbob.client.inputProcessors.MyInputProcessor;
import com.marvelousbob.client.network.MyClient;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.client.splashScreen.ISplashWorker;
import com.marvelousbob.common.network.register.dto.GameState;
import com.marvelousbob.common.network.register.dto.Msg;
import com.marvelousbob.common.network.register.dto.Ping;
import com.marvelousbob.common.network.register.dto.Player;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.GraphDrawer;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;
import space.earlygrey.shapedrawer.scene2d.GraphDrawerDrawable;

import java.util.List;

import static com.marvelousbob.client.MyGame.*;


/**
 * Takes care of setting up the most basic requirements of the game.
 * Among other things: {@link MyGame} which contains static links to "Singletons".
 */
@Slf4j
public class MarvelousBob extends Game {

    /* Splash Screen. */
    @Setter
    private ISplashWorker splashWorker;


    /**
     * Very first method called in the whole game.
     * All the static variables of {@link MyGame} are to be initialized here.
     * The calling order is most probably very sensible.
     */
    @Override
    public void create() {
        removeSplashScreen();
        createClient();

        initializeDisplayElements();
        createController();
        setUpInputProcessors();
//        displayNetworkDebuggingUi();
        instantiatePlayer();
        prepareGameState();

        changeScreen();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear the screen
        super.render(); // calls the GameScreen's `render()`

        // todo: loading screen with AssetManager, THEN `changeScreen()`
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
        getScreen().dispose();

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
        log.warn("\nisLocal? " + Boolean.parseBoolean(System.getenv("mbs_isLocal")));
        client = new MyClient(Boolean.parseBoolean(System.getenv("mbs_isLocal")));
        client.connect();
    }

    private void initializeDisplayElements() {
        /* https://github.com/raeleus/skin-composer/wiki/From-the-Ground-Up-00:-Scene2D-Primer */
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        shapeDrawer = new ShapeDrawer(stage.getBatch(),
                skin.getRegion("white"));
        font = new BitmapFont();
        root = new Table(skin);
        root.setFillParent(true);
        stage.addActor(root);
    }

    private void createController() {
        controller = new Controller();
    }

    private void setUpInputProcessors() {
        MyInputProcessor inputProcessor1 = new MyInputProcessor(stage.getCamera(),
                controller);
        MyGestureListener inputProcessor2 = new MyGestureListener(stage.getCamera(),
                controller);
        Gdx.input.setInputProcessor(new InputMultiplexer(
                stage,
                inputProcessor1,
                new GestureDetector(inputProcessor2)));
    }

    private void displayNetworkDebuggingUi() {
        root.setDebug(true);
        root.defaults().fillX().pad(20);
        addPingComponent();
        root.row();
        addMsgComponent();
        root.row();
        addGraphComponent();
    }

    private void instantiatePlayer() {
        selfPlayer = new Player();
        selfPlayer.stampNow();
        selfPlayer.setCurrX(MathUtils.random(Gdx.graphics.getWidth() - selfPlayer.getSize()));
        selfPlayer.setCurrY(MathUtils.random(Gdx.graphics.getHeight() - selfPlayer.getSize()));
    }

    private void prepareGameState() {
        gameState = new GameState();
        gameState.stampNow();
        gameState.getPlayers().add(selfPlayer);
    }

    private void changeScreen() {
        setScreen(new GameScreen()); // delegates logic (assigns "super.")
    }

    // ============================= UI ===================================


    public void addMsgComponent() {
        Table msgComponent = new Table(skin);
        Button btn2 = new TextButton("  > MSG <  ", skin);
        btn2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btn2.setDisabled(true);
                client.getClient()
                        .sendTCP(new Msg("Just a test message sent from a button."));
                btn2.setDisabled(false);
            }
        });
        msgComponent.add(btn2);
        root.add(msgComponent);
    }

    public void addPingComponent() {
        Table pingComponent = new Table(skin);
        TextArea textArea = new TextArea("placeholder", skin);
        pingComponent.add(textArea).width(300).height(90);
        Button btn = new TextButton("  > PING <  ", skin);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btn.setDisabled(true);
                for (int i = 0; i < 5; i++) {
                    client.getClient().sendTCP(new Ping(System.currentTimeMillis()));
                }
                textArea.setText(client.getLatencyReport()
                        .toString()); // todo: should be in a callback in the client?
                btn.setDisabled(false);
            }
        });
        pingComponent.add(btn);
        root.add(pingComponent);
    }


    private GraphDrawer graphDrawer;
    private GraphDrawerDrawable graphDrawerDrawable;
    private List<Float> graphData;

    public void addGraphComponent() {
        graphDrawer = new GraphDrawer(shapeDrawer);
        graphDrawerDrawable = new GraphDrawerDrawable(graphDrawer);

        graphDrawerDrawable.setColor(skin.getColor("green"));
        graphDrawerDrawable.setJoinType(JoinType.SMOOTH);
        graphDrawerDrawable.setLineWidth(2);
        graphDrawerDrawable.setSamples(100);
        graphDrawerDrawable.setDomainBegin(0);
        graphDrawerDrawable.setPlotBegin(1);
        graphDrawerDrawable.setPlotEnd(1.1f);
        Interpolation customFunc = new Interpolation() {
            @Override
            public float apply(float a) {
                int index = MathUtils.floor(a);
                int size = graphData.size();
                graphDrawerDrawable.setDomainEnd(size + .1f);
                graphDrawerDrawable.setPlotEnd(Math.max(1.1f, size - 1f));
                return size > index ? graphData.get(index) : 0;
            }
        };
        graphDrawerDrawable.setInterpolation(customFunc);

        Table graphRoot = new Table();
        root.add(graphRoot).expand().grow();

        graphRoot.pad(20);
        graphRoot.defaults().space(12);
        Image image = new Image(graphDrawerDrawable);
        graphRoot.add(image).grow();

        graphRoot.row();
        Table table = new Table();
        graphRoot.add(table).growX();

        Label label = new Label("Graph: ", skin);
        table.add(label);

        final Array<String> graphs = Array.with("Min", "Max", "Ping", "Average");
        final SelectBox<String> graphSelectBox = new SelectBox<>(skin);
        graphSelectBox.setItems(graphs);
        graphSelectBox.setSelectedIndex(2); // Ping
        graphData = client.getLatencyReport().pingData;
        table.add(graphSelectBox).uniformX().fillX();
        graphSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                IncrementalAverage average = client.getLatencyReport();
                graphData = switch (graphSelectBox.getSelected()) {
                    case "Min" -> average.minData;
                    case "Max" -> average.maxData;
                    case "Average" -> average.avgData;
                    case "Ping" -> average.pingData;
                    default -> average.pingData;
                };
            }
        });

        table.row();
        graphRoot.row();
        table = new Table();
        graphRoot.add(table);

        table.defaults().space(8).right();
        label = new Label("Samples:", skin);
        table.add(label);

        final Slider samplesSlider = new Slider(3, 300, 1, false, skin);
        samplesSlider.setValue(graphDrawerDrawable.getSamples());
        table.add(samplesSlider);

        final Label samplesLabel = new Label(
                Integer.toString(MathUtils.round(graphDrawerDrawable.getSamples())), skin);
        table.add(samplesLabel).uniformX().fillX();
        samplesSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                samplesLabel.setText(Integer.toString(MathUtils.round(samplesSlider.getValue())));
                graphDrawerDrawable.setSamples(MathUtils.round(samplesSlider.getValue()));
            }
        });
    }
}
