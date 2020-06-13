package com.marvelousbob.common.network.register;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.EndPoint;
import com.marvelousbob.common.network.register.dto.*;


public final class Register {
    private Register() {
    }

    public static void registerClasses(EndPoint registrar) {
        registrar.getKryo().register(Msg.class);
        registrar.getKryo().register(Ping.class);
        registrar.getKryo().register(GameState.class);
        registrar.getKryo().register(MoveAction.class);
        registrar.getKryo().register(Player.class);
        registrar.getKryo().register(Array.class);
    }
}
