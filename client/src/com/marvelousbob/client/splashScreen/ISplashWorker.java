package com.marvelousbob.client.splashScreen;

public interface ISplashWorker {

    /**
     * Used to stop the display of the SplashScreen once the JVM library has been loaded and the
     * game has been launched. Mostly a Desktop-related feature.
     */
    void closeSplashScreen();
}
