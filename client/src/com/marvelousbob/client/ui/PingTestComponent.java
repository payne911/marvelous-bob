package com.marvelousbob.client.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.common.network.register.dto.Msg;
import com.marvelousbob.common.network.register.dto.Ping;
import space.earlygrey.shapedrawer.GraphDrawer;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.scene2d.GraphDrawerDrawable;

import java.util.List;

import static com.marvelousbob.client.MyGame.*;

public class PingTestComponent {

    private final Table root;
    private GraphDrawer graphDrawer;
    private GraphDrawerDrawable graphDrawerDrawable;
    private List<Float> graphData;

    public PingTestComponent(Table root) {
        this.root = root;
    }

    public void displayNetworkDebuggingUi() {
        this.root.setDebug(true);
        this.root.defaults().fillX().pad(20);
        addPingComponent();
        this.root.row();
        addMsgComponent();
        this.root.row();
        addGraphComponent();
    }

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
        this.root.add(msgComponent);
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
        this.root.add(pingComponent);
    }

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
        this.root.add(graphRoot).expand().grow();

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
