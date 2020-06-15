package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.common.network.register.dto.Msg;
import com.marvelousbob.common.network.register.dto.Ping;


/**
 * The callbacks to be used on different events from the network:
 * { received, connected, disconnected, idle }
 */
public class DebugListener implements Listener {

    @Override
    public void received(Connection connection, Object receivedObject) {
        if (receivedObject instanceof Msg msg) onMsg(msg);
        if (receivedObject instanceof Ping ping) onPing(ping);
    }

    private void onPing(Ping ping) {
        MyGame.client.getLatencyReport().addToRunningAverage(ping.getTimestamp());
    }

    private void onMsg(Msg msg) {
        System.out.println(msg);
    }
}
