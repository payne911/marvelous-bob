package com.marvelousbob.client.network;

import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.client.MarvelousBob;
import com.marvelousbob.client.network.listeners.DebugListener;
import com.marvelousbob.client.network.listeners.GameInitializerListener;
import com.marvelousbob.client.network.test.IncrementalAverage;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.constants.NetworkConstants;
import com.marvelousbob.common.network.register.dto.Dto;
import java.net.InetAddress;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
public class MyClient {

    @Setter
    private IncrementalAverage latencyReport;
    private final InetAddress addr;
    private final Client client;
    private final MarvelousBob marvelousBob;
    //    private final Register entityRegister;
//    private final Register register; // todo: remove if we don't want it anymore


    @SneakyThrows
    public MyClient(boolean isLocalServer, MarvelousBob marvelousBob) {
        this.client = new Client(GameConstant.WRITE_BUFFER_SIZE, GameConstant.OBJECT_BUFFER_SIZE);
        client.getKryo().setRegistrationRequired(false);
        client.getKryo().setWarnUnregisteredClasses(true);
        this.marvelousBob = marvelousBob;
//        this.register = new Register(client);
        this.addr = isLocalServer
                ? InetAddress.getLocalHost()
                : InetAddress.getByName(NetworkConstants.REMOTE_SERVER_IP);
        this.latencyReport = new IncrementalAverage();
//        this.entityRegister = new Register(client, "com.marvelousbob.common.model.entities");
    }

    @SneakyThrows
    public void connect() {
//        register.registerClasses(Dto.class);
//        entityRegister.registerClasses();
//        client.getKryo().register(GameWorld.class, new JsonSerialization());
        client.addListener(new DebugListener());
        client.addListener(new GameInitializerListener(marvelousBob, client));

        client.start();
        client.connect(NetworkConstants.TIMEOUT, addr, NetworkConstants.PORT);
    }

    public <T extends Dto> void sendTCP(T dto) {
        client.sendTCP(dto);
    }
}
