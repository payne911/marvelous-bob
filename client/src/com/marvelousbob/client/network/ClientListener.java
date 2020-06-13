package com.marvelousbob.client.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.client.MyGame;
import com.marvelousbob.common.network.register.dto.Msg;
import com.marvelousbob.common.network.register.dto.Ping;
import com.marvelousbob.common.network.register.dto.Player;

import java.util.UUID;

/**
 * The callbacks to be used on different events from the network:
 * { received, connected, disconnected, idle }
 */
public class ClientListener extends Listener {

    @Override
    public void received(Connection connection, Object receivedObject) {
        if (receivedObject instanceof Msg msg)   onMsg(msg);
        if (receivedObject instanceof Ping ping) onPing(ping);
        if (receivedObject instanceof Player player) onPlayer(player);
    }

    private void onPlayer(Player player) {
        if (player.isSelf()){
            MyGame.selfPlayer = player;
        }
    }

    private void onUuid(UUID uuid) {
        MyGame.selfPlayer.setId(uuid);
    }

    private void onPing(Ping ping) {
        MyGame.client.getLatencyReport().addToRunningAverage(ping.getTimestamp());
    }

    private void onMsg(Msg msg) {
        System.out.println(msg);
    }
}
