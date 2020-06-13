package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.function.Consumer;

public abstract class AbstractListener<T> implements Listener, Consumer<T> {

    private Class<T> clazz;

    public AbstractListener(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (this.clazz.isAssignableFrom(object.getClass())) {
            accept((T) object);
        }
    }

}
