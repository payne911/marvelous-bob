package com.marvelousbob.common.network.customSerializers;

import com.badlogic.gdx.utils.Json;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.marvelousbob.common.state.GameWorld;

public class GameWorldSerializer extends Serializer<GameWorld> {

    @Override
    public void write(Kryo kryo, Output output, GameWorld object) {
        Json json = new Json();
        json.toJson(object);
    }

    @Override
    public GameWorld read(Kryo kryo, Input input, Class<? extends GameWorld> type) {
        return null;
    }
}
