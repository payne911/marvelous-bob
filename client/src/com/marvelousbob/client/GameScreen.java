package com.marvelousbob.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.marvelousbob.common.network.register.dto.PlayerDisconnectionDto;
import com.marvelousbob.common.utils.MovementUtils;

import static com.marvelousbob.client.MyGame.*;

/**
 * Class where all the core game's stuff happens. All our logic goes in here, and it'll be the
 * job of the {@link #render(float) render} method to
 */
public class GameScreen extends ScreenAdapter {

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.2f, .2f, .2f, 1); // sets a bg color

        updateGameState(delta);

        /* Draws that do not require Scene2d (Stage, Table, Shapes, etc.). */
        batch.begin();
        gameStateDto.getPlayerDtos().forEach(p -> {
            float someValue = 32f * Integer.parseInt(p.getId().getId());
            shapeDrawer.setColor(new Color(someValue % 8, (255f - someValue) % 8, 0f, 1f));
            shapeDrawer.rectangle(p.getCurrX(), p.getCurrY(), p.getSize(), p.getSize());
        });
        batch.end();

        /* Drawing the Scene2d stuff (UI and Actors). */
        stage.act(delta);
        stage.draw();
    }

    private void updateGameState(float delta) {
        MovementUtils.interpolatePlayers(gameStateDto, delta);
    }

    @Override
    public void dispose() {
        // anything to dispose?
        client.getClient().sendTCP(new PlayerDisconnectionDto(controller.getSelfPlayerDto().getId()));
    }
}
