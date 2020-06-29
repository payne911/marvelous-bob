package com.marvelousbob.client.inputProcessors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.marvelousbob.client.controllers.Controller;
import lombok.extern.slf4j.Slf4j;

/**
 * This is more or less the "Desktop" input processor.
 * <br>
 * The `keyTyped(char character)` method will be called repeatedly if a key is KEPT DOWN.
 */
@Slf4j
public class MyInputProcessor extends InputAdapter {

    private Camera camera;
    private Controller controller;


    public MyInputProcessor(Camera camera, Controller controller) {
        this.camera = camera;
        this.controller = controller;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 vec = camera.unproject(new Vector3(screenX, screenY, 1));
        log.debug("(%d,%d) => (%f,%f)".formatted(screenX, screenY, vec.x, vec.y));
        return false; // because we want other things to treat the mouseMoved event
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
//            case Input.Keys.SPACE:
//                var ma = new MoveAction();
//                ma.stampNow();
//                MyGame.client.getClient().sendTCP(ma);
//                System.out.println("space");
//                break;
//            case Input.Keys.ESCAPE:
//                GameState gs = new GameState();
//                gs.setP1x(234);
//                MyGame.client.getClient().sendTCP(gs);
//                System.out.println("ESCAPE");
//                break;
            default:
                log.info("keyDown_keyCode: " + keycode);
                break;
        }

        return true;
    }

    /**
     * Mouse-wheel scroll.
     *
     * @param amount Either 1 or -1, based on the direction of the scroll.
     * @return 'true' only if the event shouldn't be passed to the next InputProcessor.
     */
    @Override
    public boolean scrolled(int amount) {
//        float tmp = camera.zoom + (float)amount/4;
//        if (tmp > 0 && tmp < 2)
//            camera.zoom = tmp;
//        return true;
        return false;
    }
}