package com.marvelousbob.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.marvelousbob.client.MarvelousBob;
import com.marvelousbob.common.network.constants.GameConstant;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = GameConstant.sizeX;
        config.height = GameConstant.sizeY;

        MarvelousBob core = new MarvelousBob();
        core.setSplashWorker(new DesktopSplashWorker());
        new LwjglApplication(core, config);
    }
}
