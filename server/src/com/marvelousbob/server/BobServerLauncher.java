package com.marvelousbob.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;

public class BobServerLauncher {
    public static void main(String[] args) {

        new HeadlessApplication(new BobServerGame());
    }
}
