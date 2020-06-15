package com.marvelousbob.client.inputProcessors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.marvelousbob.client.controllers.Controller;

/**
 * This is more or less the "Desktop" input processor.
 * <br>
 * The `keyTyped(char character)` method will be called repeatedly if a key is KEPT DOWN.
 */
public class MyInputProcessor extends InputAdapter {

    private Camera camera;
    private Controller controller;


    public MyInputProcessor(Camera camera, Controller controller) {
        this.camera = camera;
        this.controller = controller;
    }


    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
//            case Input.Keys.SPACE: // todo: should TOGGLE a centerView option?
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
//            case Input.Keys.S:
//                TestClass cc = new TestClass();
//                HashMap<Integer,Integer> hm = new HashMap<>();
//                hm.put(1,5);
//                cc.setHashMap(hm);
//                MyGame.client.getClient().sendTCP(cc);
//                System.out.println("S");
//                break;
//            case Input.Keys.A:
//                Player p = new Player();
//                p.setDestY(15);
//                TestClass2 cc2 = new TestClass2();
//                cc2.setX(p);
//                MyGame.client.getClient().sendTCP(cc2);
//                System.out.println("A");
//                break;
//            case Input.Keys.D:
//                MyGame.client.getClient().sendTCP(new Player());
//                System.out.println("D");
//                break;
//            case Input.Keys.Q:
//                MyGame.client.getClient().sendTCP(new MarvelousBob());
//                System.out.println("Q");
//                break;
            default:
                System.out.println("keyDown_keyCode: " + keycode);
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