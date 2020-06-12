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
import com.marvelousbob.client.splashScreen.ISplashWorker;
import com.marvelousbob.common.register.Msg;
import com.marvelousbob.common.register.Ping;
import com.marvelousbob.common.register.Player;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.GraphDrawer;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;
import space.earlygrey.shapedrawer.scene2d.GraphDrawerDrawable;

import java.io.IOException;
import java.util.List;


/**
 * Takes care of setting up the most basic requirements of the game.
 * Among other things: {@link MyGame} which contains static links to "Singletons".
 */
@Slf4j
public class MarvelousBob extends Game {

    /* Splash Screen. */
    @Setter
    private ISplashWorker splashWorker;


    @Override
    public void create() {
        removeSplashScreen();
        createClient();

        initializeDisplayElements();
        createController();
        setUpInputProcessors();
        displayNetworkDebuggingUi();
        instanciatePlayer();

        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.2f, .2f, .2f, 1); // sets a bg color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateGameState();

        /* Draws that do not require Scene2d (Stage, Table, etc.). */
        MyGame.batch.begin();
        MyGame.shapeDrawer.rectangle(MyGame.selfPlayer.getCurrX(), MyGame.selfPlayer.getCurrY(), 40, 40);
        MyGame.batch.end();

        /* Drawing the UI over the game. */
        MyGame.stage.act();
        MyGame.stage.draw();
    }

    private void updateGameState() {
//        float x, y;
//        if (percent == 0) {
//            x = startX;
//            y = startY;
//        } else if (percent == 1) {
//            x = endX;
//            y = endY;
//        } else {
//            x = startX + (endX - startX) * percent;
//            y = startY + (endY - startY) * percent;
//        }
//        target.setPosition(x, y, alignment);
    }

    @Override
    public void resize(int width, int height) {
        // this changes viewed screen size, rather than stretch the view
        MyGame.stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        MyGame.batch.dispose();
        MyGame.skin.dispose();
        MyGame.font.dispose();
        MyGame.stage.dispose();
        try {
            MyGame.client.getClient().dispose();
        } catch (IOException e) {
            e.printStackTrace();
            MyGame.client.getClient().getUpdateThread().getThreadGroup().destroy();
        }
    }

    // ============================ INIT ==================================


    private void removeSplashScreen() {
        splashWorker.closeSplashScreen();
    }

    private void createClient() {
//        Log.set(LEVEL_TRACE);
        log.warn("\nisLocal? " + Boolean.parseBoolean(System.getenv("mbs_isLocal")));
        MyGame.client = new MyClient(Boolean.parseBoolean(System.getenv("mbs_isLocal")));
        MyGame.client.connect();
    }

    private void initializeDisplayElements() {
        /* https://github.com/raeleus/skin-composer/wiki/From-the-Ground-Up-00:-Scene2D-Primer */
        MyGame.batch = new SpriteBatch();
        MyGame.stage = new Stage(new ScreenViewport(), MyGame.batch);
        MyGame.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        MyGame.shapeDrawer = new ShapeDrawer(MyGame.stage.getBatch(),
                MyGame.skin.getRegion("white"));
        MyGame.font = new BitmapFont();
        MyGame.root = new Table(MyGame.skin);
        MyGame.root.setFillParent(true);
        MyGame.stage.addActor(MyGame.root);
    }

    private void createController() {
        MyGame.controller = new Controller();
    }

    private void setUpInputProcessors() {
        MyInputProcessor inputProcessor1 = new MyInputProcessor(MyGame.stage.getCamera(),
                MyGame.controller);
        MyGestureListener inputProcessor2 = new MyGestureListener(MyGame.stage.getCamera(),
                MyGame.controller);
        Gdx.input.setInputProcessor(new InputMultiplexer(
                MyGame.stage,
                inputProcessor1,
                new GestureDetector(inputProcessor2)));
    }

    private void displayNetworkDebuggingUi() {
        MyGame.root.setDebug(true);
        MyGame.root.defaults().fillX().pad(20);
        addPingComponent();
        MyGame.root.row();
        addMsgComponent();
        MyGame.root.row();
        addGraphComponent();
    }

    private void instanciatePlayer() {
        MyGame.selfPlayer = new Player();
        MyGame.selfPlayer.setCurrX(MathUtils.random(Gdx.graphics.getWidth()));
        MyGame.selfPlayer.setCurrY(MathUtils.random(Gdx.graphics.getHeight()));
    }

    // ============================= UI ===================================


    public void addMsgComponent() {
        Table msgComponent = new Table(MyGame.skin);
        Button btn2 = new TextButton("  > MSG <  ", MyGame.skin);
        btn2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btn2.setDisabled(true);
                MyGame.client.getClient()
                        .sendTCP(new Msg("Just a test message sent from a button."));
                btn2.setDisabled(false);
            }
        });
        msgComponent.add(btn2);
        MyGame.root.add(msgComponent);
    }

    public void addPingComponent() {
        Table pingComponent = new Table(MyGame.skin);
        TextArea textArea = new TextArea("placeholder", MyGame.skin);
        pingComponent.add(textArea).width(300).height(90);
        Button btn = new TextButton("  > PING <  ", MyGame.skin);
        btn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                btn.setDisabled(true);
                for (int i = 0; i < 500; i++) {
                    MyGame.client.getClient().sendTCP(new Ping(System.currentTimeMillis()));
                }
                textArea.setText(MyGame.client.getLatencyReport()
                        .toString()); // todo: should be in a callback in the client?
                btn.setDisabled(false);
            }
        });
        pingComponent.add(btn);
        MyGame.root.add(pingComponent);
    }


    private GraphDrawer graphDrawer;
    private GraphDrawerDrawable graphDrawerDrawable;
    private List<Float> graphData;

    public void addGraphComponent() {
        graphDrawer = new GraphDrawer(MyGame.shapeDrawer);
        graphDrawerDrawable = new GraphDrawerDrawable(graphDrawer);

        graphDrawerDrawable.setColor(MyGame.skin.getColor("green"));
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
        MyGame.root.add(graphRoot).expand().grow();

        graphRoot.pad(20);
        graphRoot.defaults().space(12);
        Image image = new Image(graphDrawerDrawable);
        graphRoot.add(image).grow();

        graphRoot.row();
        Table table = new Table();
        graphRoot.add(table).growX();

        Label label = new Label("Graph: ", MyGame.skin);
        table.add(label);

        final Array<String> graphs = Array.with("Min", "Max", "Ping", "Average");
        final SelectBox<String> graphSelectBox = new SelectBox<>(MyGame.skin);
        graphSelectBox.setItems(graphs);
        graphSelectBox.setSelectedIndex(2); // Ping
        graphData = MyGame.client.getLatencyReport().pingData;
        table.add(graphSelectBox).uniformX().fillX();
        graphSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (graphSelectBox.getSelected()) {
                    case "Min" -> graphData = MyGame.client.getLatencyReport().minData;
                    case "Max" -> graphData = MyGame.client.getLatencyReport().maxData;
                    case "Average" -> graphData = MyGame.client.getLatencyReport().avgData;
                    case "Ping" -> graphData = MyGame.client.getLatencyReport().pingData;
                    default -> graphData = MyGame.client.getLatencyReport().pingData;
                }
            }
        });

        table.row();
        graphRoot.row();
        table = new Table();
        graphRoot.add(table);

        table.defaults().space(8).right();
        label = new Label("Samples:", MyGame.skin);
        table.add(label);

        final Slider samplesSlider = new Slider(3, 300, 1, false, MyGame.skin);
        samplesSlider.setValue(graphDrawerDrawable.getSamples());
        table.add(samplesSlider);

        final Label samplesLabel = new Label(
                Integer.toString(MathUtils.round(graphDrawerDrawable.getSamples())), MyGame.skin);
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
