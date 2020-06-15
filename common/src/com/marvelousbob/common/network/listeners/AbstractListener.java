package com.marvelousbob.common.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.marvelousbob.common.network.register.dto.Dto;

public abstract class AbstractListener<T extends Dto> implements Listener, ListenerConsumer<T> {

    private Class<T> clazz;

    public AbstractListener(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void received(Connection connection, Object object) {
        if (this.clazz.isAssignableFrom(object.getClass())) {
            accept(connection, (T) object);
        }
    }

}
