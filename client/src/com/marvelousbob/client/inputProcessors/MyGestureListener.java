package com.marvelousbob.client.inputProcessors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.marvelousbob.client.controllers.Controller;
import lombok.extern.slf4j.Slf4j;

/**
 * The `float x` are the same as MyInputProcessor's `screenX` and others.
 * <p>
 * Left-click:           Button = 0
 * Right-click:          Button = 1
 * Middle-scroll-click:  Button = 2
 * <p>
 * Keep in mind this class extends the Adapter which itself implements the
 * interface. There are other utility methods to be overridden.
 */
@Slf4j
public class MyGestureListener extends GestureDetector.GestureAdapter {

    private final Camera camera;
    private final Controller controller;


    public MyGestureListener(Camera camera, Controller controller) {
        this.camera = camera;
        this.controller = controller;
    }

    /**
     * Converts a click's position into a Tile coordinate, and then assigns the appropriate action
     * according to what is on the Tile that was clicked.
     *
     * @param x      screen x-coordinate
     * @param y      screen y-coordinate
     * @param count  ?
     * @param button left(0), right(1), and middle(2)         mouse-click.
     * @return 'true' only if the event shouldn't be passed to the next InputProcessor.
     */
    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 vec = camera.unproject(new Vector3(x, y, 1));
        log.debug("(%f,%f) => (%f,%f)".formatted(x, y, vec.x, vec.y));
        controller.playerTapped(vec.x, vec.y);
        return true;
    }

}
