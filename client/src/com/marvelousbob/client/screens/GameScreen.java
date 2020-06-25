package com.marvelousbob.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.marvelousbob.client.entities.GameWorld;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import com.marvelousbob.common.utils.MovementUtils;
import lombok.Getter;

import static com.marvelousbob.client.MyGame.*;

/**
 * Class where all the core game's stuff happens. All our logic goes in here, and it'll be the job
 * of the {@link #render(float) render} method to handle all of that (it's the Game Loop).
 * <p>
 * This class is instantiated by the {@link com.esotericsoftware.kryonet.Client Kryo Client} in
 * {@link com.marvelousbob.client.network.listeners.GameInitializerListener}.
 */
public class GameScreen extends ScreenAdapter {

    @Getter
    private final GameWorld gameWorld;
    private final ParticleEffect effect = new ParticleEffect();
    private static final float PARTICLE_EFFECT_SCALE = .5f;

    public GameScreen(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void show() {
        effect.load(Gdx.files.internal("particles/emitters/BlueFlames"),
                Gdx.files.internal("particles"));
        effect.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        effect.scaleEffect(PARTICLE_EFFECT_SCALE);
        effect.start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.2f, .2f, .2f, 1); // sets a bg color

        updateGameState(delta);

        /* Draws that do not require Scene2d (Stage, Table, Shapes, etc.). */
        batch.begin();
        controller.getLocalState().getPlayersDtos()
                .forEach((uuid, p) -> {
                    shapeDrawer.setColor(GameConstant.playerColors.get(p.getColorIndex()));
                    shapeDrawer.rectangle(p.getCurrX(), p.getCurrY(), p.getSize(), p.getSize());
                });
        gameWorld.drawMe(shapeDrawer);
        effect.draw(shapeDrawer.getBatch(), delta);
        batch.end();

        /* Drawing the Scene2d stuff (UI and Actors). */
        stage.act(delta);
        stage.draw();
    }

    private void updateGameState(float delta) {
        updateParticles(delta);
        boolean hasMoved = MovementUtils.interpolatePlayers(controller.getLocalState(), delta);
        if (hasMoved) {
            controller.registerCurrentLocalState();
        }
    }

    private void updateParticles(float delta) {
        // for changing angle, see: https://gamedev.stackexchange.com/a/110725/130731
        effect.update(delta);
        if (effect.isComplete()) {
            effect.reset();
            effect.scaleEffect(PARTICLE_EFFECT_SCALE);
        }
    }

    @Override
    public void dispose() {
        // anything to dispose?
        client.getClient()
                .sendTCP(new PlayerDisconnectionDto(controller.getSelfPlayerDto().getUuid()));
        effect.dispose();
    }
}
