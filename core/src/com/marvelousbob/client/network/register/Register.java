package com.marvelousbob.client.network.register;

import com.esotericsoftware.kryonet.EndPoint;


public class Register {

    public static void registerClasses(EndPoint registrar) {
        registrar.getKryo().register(Msg.class);
        registrar.getKryo().register(Ping.class);
        registrar.getKryo().register(GameState.class);
        registrar.getKryo().register(MoveAction.class);
        registrar.getKryo().register(TestClass.class);
        registrar.getKryo().register(TestClass2.class);
    }
}
