package com.marvelousbob.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.MoveActionDto;

public class MoveActionListener extends AbstractListener<MoveActionDto> {
    public MoveActionListener() {
        super(MoveActionDto.class);
    }

    @Override
    public void accept(Connection connection, MoveActionDto moveActionDto) {

    }
}
