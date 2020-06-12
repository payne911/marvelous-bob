package com.marvelousbob.client.inputProcessors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.marvelousbob.client.controllers.Controller;

/**
 * The `float x` are the same as MyInputProcessor's `screenX` and others.
 * <p>
 * Left-click:              Button = 0 Right-click:             Button = 1 Middle-scroll-click:
 * Button = 2
 */
public class MyGestureListener implements GestureDetector.GestureListener {

    private Camera camera;
    private Controller controller;


    public MyGestureListener(Camera camera, Controller controller) {
        this.camera = camera;
        this.controller = controller;
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("touchDown: " + x + "," + y + " | pointer: " + pointer + " | button: " + button);
        return false;
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
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("tap: " + x + "," + y + " | count: " + count + " | button: " + button);

        Vector3 vec = camera.unproject(new Vector3(x, y, 1));
        System.out.println("(%f,%f) => (%f,%f)".formatted(x, y, vec.x, vec.y));
        controller.playerTapped(vec.x, vec.y);

        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("longPress: " + x + "," + y);
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("fling | velocityX: " + velocityX + " | velocityY: " + velocityY + " | button: " + button);
        return false;
    }


    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("panStop: " + x + "," + y + " | pointer: " + pointer + " | button: " + button);
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("zoom | initialDistance: " + initialDistance + " | distance: " + distance);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
                         Vector2 pointer2) {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("pinch");
        return false;
    }

    @Override
    public void pinchStop() {
//        if(GameLogic.DEBUG_GESTURE_PRINT) System.out.println("pinchStop");
    }
}
