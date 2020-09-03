package com.marvelousbob.client.screens;

import static com.marvelousbob.client.MyGame.batch;
import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.shapeDrawer;
import static com.marvelousbob.client.MyGame.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.BloomEffect.Settings;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.BatchRendererAdapter;
import com.crashinvaders.vfx.framebuffer.VfxFrameBuffer.Renderer;
import com.marvelousbob.client.controllers.Controller;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import lombok.Getter;

/**
 * Class where all the core game's stuff happens. All our logic goes in here, and it'll be the job
 * of the {@link #render(float) render} method to handle all of that (it's the Game Loop).
 * <p>
 * This class is instantiated by the {@link com.esotericsoftware.kryonet.Client Kryo Client} in
 * {@link com.marvelousbob.client.network.listeners.GameInitializerListener}.
 */
public class GameScreen extends ScreenAdapter {

    @Getter
    private final Controller controller;
    private final ParticleEffect effect = new ParticleEffect();
    private static final float PARTICLE_EFFECT_SCALE = .5f;
    private final VfxManager vfxManager;
    private final BloomEffect bloomEffect;

    public GameScreen(Controller controller) {
        this.controller = controller;

        /* Shaders stuff. */
        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
        bloomEffect = new BloomEffect(new Settings(8, 0.15f, 1f, .85f, 1.9f, .85f));
        vfxManager.addEffect(bloomEffect);
        Renderer batchRenderer = new BatchRendererAdapter(batch); // to ensure resolution is good
        var buffer = vfxManager.getPingPongWrapper().getSrcBuffer();
        var buffer2 = vfxManager.getPingPongWrapper().getDstBuffer();
        buffer.addRenderer(batchRenderer);
        buffer2.addRenderer(batchRenderer);
    }

    @Override
    public void show() {
        /* Particle effect. */
        effect.load(Gdx.files.internal("particles/emitters/BlueFlames"),
                Gdx.files.internal("particles"));
        effect.setPosition(GameConstant.SIZE_X / 2f, GameConstant.SIZE_Y / 2f);
        effect.scaleEffect(PARTICLE_EFFECT_SCALE);
        effect.start();
    }

    @Override
    public void resize(int width, int height) {
//        /*
//        The following resize strategy will ensure that you will always see
//        30 units in the x axis no matter what pixel-width your device has.
//         */
//        camera.viewportWidth  = 30f;                // Viewport of 30 units!
//        camera.viewportHeight = 30f * height/width; // Lets keep things in proportion.
//        camera.update();

//        /* The following resize strategy will show less/more of the world depending on the resolution. */
//        camera.viewportWidth  = width/32f;  //We will see width/32f units!
//        camera.viewportHeight = camera.viewportWidth * height/width;
//        camera.update();

        vfxManager.resize(width, height);
        stage.getViewport().update(width, height, true);
//        batch.setProjectionMatrix(stage.getViewport().getCamera().projection);
//        batch.setTransformMatrix(stage.getViewport().getCamera().view);
    }

    @Override
    public void render(float delta) {
        updateGameState(delta);

        vfxManager.cleanUpBuffers(); // we don't need any information from the last render
        vfxManager.beginInputCapture(); // begin render to an off-screen buffer

        /* Draws that do not require Scene2d (Stage, Table, Shapes, etc.). */
        drawWorld();

        vfxManager.endInputCapture(); // end render to an off-screen buffer.
        vfxManager.applyEffects();
        vfxManager.renderToScreen();

        /* Drawing the Scene2d stuff (UI and Actors). */
        drawUi(delta);
    }

    private void drawWorld() {
        batch.begin();
        controller.getGameWorld().drawMe(shapeDrawer);
//        effect.draw(shapeDrawer.getBatch(), delta);
        batch.end();
    }

    private void drawUi(float delta) {
        stage.act(delta);
        stage.draw();
    }

    private void updateGameState(float delta) {
        updateParticles(delta);
        controller.updateGameState(delta);
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
        client.getClient()
                .sendTCP(new PlayerDisconnectionDto(controller.getSelfPlayerUuid()));
        effect.dispose();

        /* Shader stuff. */
        vfxManager.dispose();
        bloomEffect.dispose();
    }
}
